/*******************************************************************************
 * Copyright (c) 2016 BestSolution.at and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tom Schindl<tom.schindl@bestsolution.at> - initial API and implementation
 *******************************************************************************/
package org.eclipse.fx.core.property;

import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.eclipse.fx.core.Subscription;
import org.eclipse.fx.core.ThreadSynchronize;

import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * Collection of utilities for JavaFX {@link Observable}
 *
 * @since 3.0
 */
public class FXObservableUtils{
	/**
	 * Bind the content to the source list to the target and apply the converter
	 * in between
	 *
	 * @param target
	 *            the target list
	 * @param sourceList
	 *            the source list
	 * @param converterFunction
	 *            the function used to convert
	 * @param <T>
	 *            the target type
	 * @param <E>
	 *            the source type
	 * @return the subscription to dispose the binding
	 */
	public static <T, E> Subscription bindContent(List<T> target, ObservableList<E> sourceList, Function<E, T> converterFunction) {
		return bindContent(null, target, sourceList, converterFunction);
	}

	/**
	 * Bind the content to the source list to the target and apply the converter
	 * in between
	 *
	 * @param threadSync
	 *            strategy to synchronize the target on a certain thread, might be <code>null</code>
	 * @param target
	 *            the target list
	 * @param sourceList
	 *            the source list
	 * @param converterFunction
	 *            the function used to convert
	 * @param <T>
	 *            the target type
	 * @param <E>
	 *            the source type
	 * @return the subscription to dispose the binding
	 */
	public static <T, E> Subscription bindContent(ThreadSynchronize threadSync, List<T> target, ObservableList<E> sourceList, Function<E, T> converterFunction) {
		List<T> list = sourceList.stream().map(converterFunction).collect(Collectors.toList());

		if( threadSync == null ) {
			if (target instanceof ObservableList<?>) {
				((ObservableList<T>) target).setAll(list);
			} else {
				target.clear();
				target.addAll(list);
			}
		} else {
			threadSync.asyncExec(() -> {
				if (target instanceof ObservableList<?>) {
					((ObservableList<T>) target).setAll(list);
				} else {
					target.clear();
					target.addAll(list);
				}
			});
		}
		ListChangeListener<E> fl;

		ListChangeListener<E> l = change -> {
			while (change.next()) {
				if (change.wasPermutated()) {
					target.subList(change.getFrom(), change.getTo()).clear();
					target.addAll(change.getFrom(), transformList(change.getList().subList(change.getFrom(), change.getTo()), converterFunction));
				} else {
					if (change.wasRemoved()) {
						target.subList(change.getFrom(), change.getFrom() + change.getRemovedSize()).clear();
					}
					if (change.wasAdded()) {
						target.addAll(change.getFrom(), transformList(change.getAddedSubList(), converterFunction));
					}
				}
			}
		};
		if( threadSync == null ) {
			fl = l;
			sourceList.addListener(l);
		} else {
			fl = change -> {
				threadSync.asyncExec( () -> l.onChanged(change) );
			};
			sourceList.addListener( fl );
		}

		return new Subscription() {

			@Override
			public void dispose() {
				if( threadSync == null ) {
					sourceList.removeListener(fl);
				} else {
					threadSync.asyncExec(fl, sourceList::removeListener);
				}
			}
		};
	}

	/**
	 * Binding between 2 lists with an optional padding
	 *
	 * @param <T>
	 *            the target type
	 * @param <E>
	 *            the source type
	 */
	public static class PaddedListBinding<T, E> implements Subscription {
		final ObservableList<E> sourceList;
		final IntegerProperty padding;
		final ListChangeListener<E> l;
		final IntFunction<T> paddingEntryFactory;
		final List<T> target;
		final ThreadSynchronize threadSync;

		PaddedListBinding(ThreadSynchronize threadSync, int padding, List<T> target, ObservableList<E> sourceList, Function<E, T> converterFunction, IntFunction<T> paddingEntryFactory) {
			this.threadSync = threadSync;
			this.padding = new SimpleIntegerProperty(this, "padding", padding); //$NON-NLS-1$
			this.target = target;
			this.sourceList = sourceList;
			ListChangeListener<E> l = change -> {
				while (change.next()) {
					if (change.wasPermutated()) {
						target.subList(change.getFrom() + getPadding(), change.getTo() + getPadding()).clear();
						target.addAll(change.getFrom() + getPadding(), transformList(change.getList().subList(change.getFrom(), change.getTo()), converterFunction));
					} else {
						if (change.wasRemoved()) {
							target.subList(change.getFrom() + getPadding(), change.getFrom() + getPadding() + change.getRemovedSize()).clear();
						}
						if (change.wasAdded()) {
							target.addAll(change.getFrom() + getPadding(), transformList(change.getAddedSubList(), converterFunction));
						}
					}
				}
			};

			if( threadSync == null ) {
				this.l = l;
			} else {
				ListChangeListener<E> ll = change -> {
					threadSync.asyncExec( () -> l.onChanged(change) );
				};
				this.l = ll;
			}

			this.sourceList.addListener(this.l);
			this.paddingEntryFactory = paddingEntryFactory;
			this.padding.addListener((o, ol, ne) -> {
				int iOl = ol == null ? 0 : ol.intValue();
				int iNe = ne == null ? 0 : ne.intValue();

				if (iOl > iNe) {
					target.subList(iNe, iOl).clear();
				} else if (iOl < iNe) {
					List<T> paddedObjects = IntStream.range(iOl, iNe).mapToObj(paddingEntryFactory).collect(Collectors.toList());
					target.addAll(iOl, paddedObjects);
				}
			});

			List<T> list = sourceList.stream().map(converterFunction).collect(Collectors.toList());

			if( threadSync == null ) {
				if (padding == 0) {
					if (target instanceof ObservableList<?>) {
						((ObservableList<T>) target).setAll(list);
					} else {
						target.clear();
						target.addAll(list);
					}
				} else {
					if (!target.isEmpty()) {
						target.subList(padding, target.size()).clear();
						target.addAll(padding, list);
					} else {
						List<T> t = IntStream.range(0, padding).mapToObj(paddingEntryFactory).collect(Collectors.toList());
						t.addAll(list);
						target.addAll(t);
					}
				}
			} else {
				threadSync.asyncExec(() -> {
					if (padding == 0) {
						if (target instanceof ObservableList<?>) {
							((ObservableList<T>) target).setAll(list);
						} else {
							target.clear();
							target.addAll(list);
						}
					} else {
						if (!target.isEmpty()) {
							target.subList(padding, target.size()).clear();
							target.addAll(padding, list);
						} else {
							List<T> t = IntStream.range(0, padding).mapToObj(paddingEntryFactory).collect(Collectors.toList());
							t.addAll(list);
							target.addAll(t);
						}
					}
				});
			}
		}

		/**
		 * @return padding for the target list
		 */
		public final IntegerProperty paddingProperty() {
			return this.padding;
		}

		/**
		 * @return the padding
		 */
		public final int getPadding() {
			return this.paddingProperty().get();
		}

		/**
		 * Set a new padding
		 *
		 * @param padding
		 *            a new padding
		 */
		public final void setPadding(final int padding) {
			this.paddingProperty().set(padding);
		}

		@Override
		public void dispose() {
			if( this.threadSync == null ) {
				this.sourceList.remove(this.l);
			} else {
				this.threadSync.asyncExec(this.l, this.sourceList::remove);
			}

		}
	}

	/**
	 * Bind the content to the source list to the target with an optional
	 * padding and apply the converter in between
	 *
	 * @param padding
	 *            the initial padding
	 * @param target
	 *            the target
	 * @param sourceList
	 *            the source list
	 * @param converterFunction
	 *            the converter function
	 * @param paddingEntryFactory
	 *            function to consult when filling padding slots
	 * @return the binding
	 */
	public static <T, E> PaddedListBinding<T, E> bindContent(int padding, List<T> target, ObservableList<E> sourceList, Function<E, T> converterFunction, IntFunction<T> paddingEntryFactory) {
		return bindContent(null, padding, target, sourceList, converterFunction, paddingEntryFactory);
	}

	/**
	 * Bind the content to the source list to the target with an optional
	 * padding and apply the converter in between
	 *
	 * @param threadSync
	 *            strategy to synchronize the target on a certain thread, might be <code>null</code>
	 * @param padding
	 *            the initial padding
	 * @param target
	 *            the target
	 * @param sourceList
	 *            the source list
	 * @param converterFunction
	 *            the converter function
	 * @param paddingEntryFactory
	 *            function to consult when filling padding slots
	 * @return the binding
	 */
	public static <T, E> PaddedListBinding<T, E> bindContent(ThreadSynchronize threadSync, int padding, List<T> target, ObservableList<E> sourceList, Function<E, T> converterFunction, IntFunction<T> paddingEntryFactory) {
		return new PaddedListBinding<>(threadSync, padding, target, sourceList, converterFunction, paddingEntryFactory);
	}

	/**
	 * Transform the list to another list
	 *
	 * @param list
	 *            the list
	 * @param converterFunction
	 *            the converter function
	 * @return the list
	 */
	public static <T, E> List<T> transformList(List<? extends E> list, Function<E, T> converterFunction) {
		return list.stream().map(converterFunction).collect(Collectors.toList());
	}
}