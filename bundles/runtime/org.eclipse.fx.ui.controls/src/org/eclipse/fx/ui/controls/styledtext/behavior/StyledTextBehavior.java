/*******************************************************************************
 * Copyright (c) 2014 BestSolution.at and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * 	Tom Schindl<tom.schindl@bestsolution.at> - initial API and implementation
 *******************************************************************************/
package org.eclipse.fx.ui.controls.styledtext.behavior;

import java.util.List;

import org.eclipse.fx.core.Util;
import org.eclipse.fx.ui.controls.styledtext.ActionEvent;
import org.eclipse.fx.ui.controls.styledtext.ActionEvent.ActionType;
import org.eclipse.fx.ui.controls.styledtext.StyledTextArea;
import org.eclipse.fx.ui.controls.styledtext.StyledTextLayoutContainer;
import org.eclipse.fx.ui.controls.styledtext.TextSelection;
import org.eclipse.fx.ui.controls.styledtext.VerifyEvent;
import org.eclipse.fx.ui.controls.styledtext.skin.StyledTextSkin.LineCell;

import javafx.event.Event;
import javafx.geometry.Bounds;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * Behavior for styled text
 */
public class StyledTextBehavior {
	private final StyledTextArea styledText;

	/**
	 * Create a new behavior
	 *
	 * @param styledText
	 *            the styled text control
	 */
	public StyledTextBehavior(StyledTextArea styledText) {
		this.styledText = styledText;
		styledText.addEventHandler(KeyEvent.ANY, this::callActionForEvent);
	}

	/**
	 * @return the control
	 */
	protected StyledTextArea getControl() {
		return this.styledText;
	}

	/**
	 * Handle key event
	 *
	 * @param arg0
	 *            the event
	 */
	protected void callActionForEvent(KeyEvent arg0) {
		if (arg0.getEventType() == KeyEvent.KEY_PRESSED) {
			_keyPressed(arg0);
		} else if (arg0.getEventType() == KeyEvent.KEY_TYPED) {
			_keyTyped(arg0);
		}
	}

	/**
	 * handle the mouse pressed
	 *
	 * @param arg0
	 *            the mouse event
	 */
	public void mousePressed(MouseEvent arg0) {
		getControl().requestFocus();
	}

	/**
	 * Invoke an action
	 *
	 * @param action
	 *            the action
	 */
	protected void invokeAction(ActionType action) {
		ActionEvent evt = new ActionEvent(getControl(), getControl(), action);
		Event.fireEvent(getControl(), evt);
	}

	@SuppressWarnings("deprecation")
	private void _keyPressed(KeyEvent event) {
		VerifyEvent evt = new VerifyEvent(getControl(), getControl(), event);
		Event.fireEvent(getControl(), evt);

		// Bug in JavaFX who enables the menu when ALT is pressed
		if (Util.isMacOS()) {
			if (event.getCode() == KeyCode.ALT || event.isAltDown()) {
				event.consume();
			}
		}

		if (evt.isConsumed()) {
			event.consume();
			return;
		}

		int currentRowIndex = getControl().getContent().getLineAtOffset(getControl().getCaretOffset());

		final int offset = getControl().getCaretOffset();

		switch (event.getCode()) {
		case SHIFT:
		case ALT:
		case CONTROL:
			break;
		case LEFT: {
			if (event.isAltDown()) {
				invokeAction(ActionType.WORD_PREVIOUS);
			} else {
				if (offset == 0) {
					event.consume();
					break;
				}
				int newOffset = offset - 1;
				@SuppressWarnings("unused")
				int currentLine = getControl().getContent().getLineAtOffset(offset);
				@SuppressWarnings("unused")
				int newLine = getControl().getContent().getLineAtOffset(newOffset);
				getControl().impl_setCaretOffset(newOffset, event.isShiftDown());
				event.consume();
			}
			break;
		}
		case RIGHT: {
			if (event.isAltDown()) {
				invokeAction(ActionType.WORD_NEXT);
			} else if (event.isMetaDown()) {
				int currentLine = getControl().getContent().getLineAtOffset(offset);
				int lineOffset = getControl().getContent().getOffsetAtLine(currentLine);
				String lineContent = getControl().getContent().getLine(currentLine);

				getControl().impl_setCaretOffset(lineOffset + lineContent.length(), event.isShiftDown());
				event.consume();
			} else {
				if (offset + 1 > getControl().getContent().getCharCount()) {
					break;
				}
				int newOffset = offset + 1;
				// @SuppressWarnings("unused")
				// int currentLine =
				// getControl().getContent().getLineAtOffset(offset);
				// @SuppressWarnings("unused")
				// int newLine =
				// getControl().getContent().getLineAtOffset(newOffset);
				getControl().impl_setCaretOffset(newOffset, event.isShiftDown());
				event.consume();
			}
			break;
		}
		case UP: {
			int rowIndex = currentRowIndex;

			if (rowIndex == 0) {
				break;
			}

			int colIdx = offset - getControl().getContent().getOffsetAtLine(rowIndex);
			rowIndex -= 1;

			int lineOffset = getControl().getContent().getOffsetAtLine(rowIndex);
			int newCaretPosition = lineOffset + colIdx;
			int maxPosition = lineOffset + getControl().getContent().getLine(rowIndex).length();

			getControl().impl_setCaretOffset(Math.min(newCaretPosition, maxPosition), event.isShiftDown());
			event.consume();
			break;
		}
		case DOWN: {
			int rowIndex = currentRowIndex;
			if (rowIndex + 1 == getControl().getContent().getLineCount()) {
				break;
			}

			int colIdx = offset - getControl().getContent().getOffsetAtLine(rowIndex);
			rowIndex += 1;

			int lineOffset = getControl().getContent().getOffsetAtLine(rowIndex);
			int newCaretPosition = lineOffset + colIdx;
			int maxPosition = lineOffset + getControl().getContent().getLine(rowIndex).length();

			getControl().impl_setCaretOffset(Math.min(newCaretPosition, maxPosition), event.isShiftDown());
			event.consume();
			break;
		}
		case ENTER:
			if (getControl().getEditable()) {
				int line = getControl().getContent().getLineAtOffset(getControl().getCaretOffset());
				String lineContent = getControl().getContent().getLine(line);

				// FIXME Temp hack
				char[] chars = lineContent.toCharArray();
				String prefix = ""; //$NON-NLS-1$
				for (int i = 0; i < chars.length; i++) {
					if (chars[i] == ' ') {
						prefix += " "; //$NON-NLS-1$
					} else {
						break;
					}
				}

				String newLine = System.getProperty("line.separator"); //$NON-NLS-1$

				getControl().getContent().replaceTextRange(getControl().getCaretOffset(), 0, newLine + prefix);
				// listView.getSelectionModel().select(listView.getSelectionModel().getSelectedIndex()+1);
				getControl().setCaretOffset(offset + newLine.length() + prefix.length());
			}
			break;
		case DELETE:
			if (getControl().getEditable()) {
				if (event.isMetaDown()) {
					invokeAction(ActionType.DELETE_WORD_NEXT);
				} else {
					getControl().getContent().replaceTextRange(getControl().getCaretOffset(), 1, ""); //$NON-NLS-1$
					getControl().setCaretOffset(offset);
				}
				break;
			}
		case BACK_SPACE:
			if (getControl().getEditable()) {
				if (event.isMetaDown()) {
					invokeAction(ActionType.DELETE_WORD_PREVIOUS);
				} else {
					TextSelection selection = getControl().getSelection();
					if (selection.length > 0) {
						getControl().getContent().replaceTextRange(selection.offset, selection.length, ""); //$NON-NLS-1$
						getControl().setCaretOffset(selection.offset);
					} else {
						getControl().getContent().replaceTextRange(getControl().getCaretOffset() - 1, 1, ""); //$NON-NLS-1$
						getControl().setCaretOffset(offset - 1);
					}
				}
				break;
			}
		case TAB:
			if (getControl().getEditable()) {
				event.consume();
				if (event.isShiftDown()) {
					// TODO Remove first 4 white space chars???
					break;
				} else {
					getControl().getContent().replaceTextRange(getControl().getCaretOffset(), 0, "\t"); //$NON-NLS-1$
					getControl().setCaretOffset(offset + 1);
					break;
				}
			}
		case V:
			if (getControl().getEditable()) {
				if (event.isShortcutDown()) {
					getControl().paste();
					event.consume();
					break;
				}
			}
		case X:
			if (getControl().getEditable()) {
				if (event.isShortcutDown()) {
					getControl().cut();
					event.consume();
					break;
				}
			}
		case C: {
			if (event.isShortcutDown()) {
				getControl().copy();
				event.consume();
				break;
			}
		}
		default:
			break;
		}
	}

	private void _keyTyped(KeyEvent event) {
		if (getControl().getEditable()) {

			String character = event.getCharacter();
			if (character.length() == 0) {
				return;
			}

			// check the modifiers
			// - OS-X: ALT+L ==> @
			// - win32/linux: ALTGR+Q ==> @
			if (event.isControlDown() || event.isAltDown() || (Util.isMacOS() && event.isMetaDown())) {
				if (!((event.isControlDown() || Util.isMacOS()) && event.isAltDown()))
					return;
			}

			if (character.charAt(0) > 31 // No ascii control chars
					&& character.charAt(0) != 127 // no delete key
					&& !event.isMetaDown()) {
				final int offset = getControl().getCaretOffset();
				getControl().getContent().replaceTextRange(getControl().getCaretOffset(), 0, character);
				getControl().setCaretOffset(offset + 1);
			}
		}
	}

	/**
	 * Send a mouse pressed
	 *
	 * @param event
	 *            the event
	 * @param visibleCells
	 *            the visible cells
	 * @param selection
	 *            are we in selection mode
	 */
	@SuppressWarnings("deprecation")
	public void updateCursor(MouseEvent event, List<LineCell> visibleCells, boolean selection) {
		LineCell lastCell = null;

		for (LineCell tmp : visibleCells) {
			Bounds boundsInParent = tmp.getBoundsInParent();
			if (boundsInParent.getMinY() > event.getY()) {
				if (lastCell == null) {
					lastCell = tmp;
				}

				if (lastCell.getDomainElement() != null) {
					StyledTextLayoutContainer n = (StyledTextLayoutContainer) lastCell.getGraphic();
					if (n.localToScene(n.getBoundsInLocal()).contains(event.getSceneX(), event.getSceneY())) {
						int index = n.getCaretIndexAtPoint(n.sceneToLocal(event.getSceneX(), event.getSceneY()));
						if (index >= 0) {
							getControl().impl_setCaretOffset(n.getStartOffset() + index, selection);
							return;
						}
					}

					int offset = lastCell.getDomainElement().getLineOffset() + lastCell.getDomainElement().getLineLength();
					getControl().impl_setCaretOffset(offset, selection);

				}
				break;
			}
			lastCell = tmp;
		}
		getControl().requestFocus();
		Event.fireEvent(getControl(), event.copyFor(getControl(), getControl()));
	}

	// public void mouseDragged(MouseEvent event, List<LineCell> visibleCells) {
	//
	// }
}
