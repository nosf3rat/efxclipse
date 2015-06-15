/*******************************************************************************
 * Copyright (c) 2014 BestSolution.at and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tom Schindl <tom.schindl@bestsolution.at> - initial API and implementation
 *******************************************************************************/
package org.eclipse.fx.core;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A return value definition
 *
 * @param <V>
 *            the returned value type
 */
public interface ReturnValue<V> {
	/**
	 * @return the state
	 */
	@NonNull
	public State getState();

	/**
	 * @return the message in case of none OK or CANCEL
	 */
	@Nullable
	public String getMessage();

	/**
	 * @return the value in case of OK
	 */
	@Nullable
	public V getValue();

	/**
	 * @return the exception causing the failure if one
	 */
	@Nullable
	public Throwable getThrowable();

	/**
	 * Create an ok return value
	 *
	 * @param value
	 *            the value
	 * @return the return value
	 * @since 1.2
	 */
	public static <V> @NonNull ReturnValue<V> ok(@Nullable V value) {
		return new ReturnValueImpl<V>(State.OK, null, value, null);
	}

	/**
	 * Create an error return value
	 *
	 * @param message
	 *            the message
	 * @param error
	 *            the error
	 * @return the return value
	 * @since 1.2
	 */
	public static <V> @NonNull ReturnValue<V> error(@NonNull String message,
			@NonNull Throwable error) {
		return new ReturnValueImpl<V>(State.ERROR, message, null, error);
	}

	/**
	 * State of the method a callback
	 */
	public enum State {
		/**
		 * Action ended in error
		 */
		ERROR,
		/**
		 * Action ended with a warning
		 */
		WARNING,
		/**
		 * Action was canceled
		 */
		CANCEL,
		/**
		 * Action succeeded
		 */
		OK
	}

	/**
	 * Basic return value hold information for the callback
	 *
	 * @param <V>
	 *            the value type
	 * @deprecated use {@link ReturnValue#ok(Object)}
	 */
	@Deprecated
	public static class ReturnValueImpl<V> implements ReturnValue<V> {
		/**
		 * The state
		 */
		@NonNull
		public final State state;
		/**
		 * A message in case of a state different than OK or CANCEL
		 */
		@Nullable
		public final String message;
		/**
		 * An exception in case one was the reason for the failure
		 */
		@Nullable
		public final Throwable throwable;
		/**
		 * The value in case of a OK state
		 */
		@Nullable
		public final V value;

		/**
		 * Create a new return value
		 *
		 * @param state
		 *            the state
		 * @param message
		 *            the message
		 * @param value
		 *            the value
		 * @param throwable
		 *            the exception
		 */
		public ReturnValueImpl(@NonNull State state, @Nullable String message,
				@Nullable V value, @Nullable Throwable throwable) {
			this.state = state;
			this.message = message;
			this.throwable = throwable;
			this.value = value;
		}

		@Override
		public String getMessage() {
			return this.message;
		}

		@Override
		public Throwable getThrowable() {
			return this.throwable;
		}

		@Override
		public State getState() {
			return this.state;
		}

		@Override
		public V getValue() {
			return this.value;
		}
	}
}