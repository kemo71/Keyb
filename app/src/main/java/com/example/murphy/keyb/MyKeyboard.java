package com.example.murphy.keyb;

/**
 * <h1>Android Arabic / Hebrew / English Keyboard</h1>
 *
 * Final Year Computer Science project
 *
 * @author  Colm Murphy 112713751
 * @version 1.0
 * @since   2016-04-07
 * 
 */

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.FrameLayout;
import android.widget.PopupWindow;


public class MyKeyboard extends InputMethodService
        implements KeyboardView.OnKeyboardActionListener{

    private KeyboardView kv;
    private Keyboard keyboard;
    private static final int KEYCODE_SYM_NUM = -7;
    private static final int KEYCODE_MORE_SYM = -8;
    private boolean caps = false;


    /**
     * Create the Input view
     *
     * @return kv, the Keyboard view object
     */
    @Override
    public View onCreateInputView() {
        kv = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard, null);
        switchKeyboard(R.xml.arabic);
        return kv;
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    /**
     * Listens for the key press.
     * Listens for the key press and
     * delegates the appropriate action.
     *
     * @param primaryCode
     * @param keyCodes
     */
    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();

        switch (primaryCode){
            case Keyboard.KEYCODE_MODE_CHANGE:
                switchKeyboard(R.xml.arabic);
                break;
            case Keyboard.KEYCODE_ALT:
                switchKeyboard(R.xml.qwerty);
                break;
             case Keyboard.KEYCODE_CANCEL:
               switchKeyboard(R.xml.hebrew);
                 break;
            case MyKeyboard.KEYCODE_SYM_NUM:
                 switchKeyboard(R.xml.sym_numbers);
                 break;
            case MyKeyboard.KEYCODE_MORE_SYM:
                switchKeyboard(R.xml.more_symbols);
                break;
            case Keyboard.KEYCODE_DELETE:
                ic.deleteSurroundingText(1, 0);
                break;
            case Keyboard.KEYCODE_SHIFT:
                caps = !caps;
                keyboard.setShifted(caps);
                kv.invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;
            default:
                char code = (char) primaryCode;
                if(Character.isLetter(code) && caps) {
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code), 1);
        }


    }

    /**
     * Switches the Keyboard.
     * Set the keyboard to onr of the predefined xml files
     *
     * @param keyboardRes
     */
    private void switchKeyboard(int keyboardRes) {
        keyboard = new Keyboard(this, keyboardRes);
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
    }

    /**
    * All the remaining methods in this class are
    * not used. They are included for adding
    * extra functionality in the future
    */
    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }


}