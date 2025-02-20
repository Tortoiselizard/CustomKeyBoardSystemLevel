package com.example.customkeyboardsystemlevel

import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.view.View
import android.view.inputmethod.InputConnection

class CustomKeyboardApp : InputMethodService(), KeyboardView.OnKeyboardActionListener {

    override fun onCreateInputView(): View {
        val keyboardView = layoutInflater.inflate(R.layout.custom_keyboard_layout, null) as KeyboardView
        val keyboard = Keyboard(this, R.xml.custom_keypad)
        keyboardView.keyboard = keyboard
        keyboardView.setOnKeyboardActionListener(this)
        return keyboardView
    }

    override fun onPress(i: Int) {}
    override fun onRelease(i: Int) {}

    override fun onKey(i: Int, keyCodes: IntArray?) {
        val inputConnection = currentInputConnection ?: return
        inputConnection.commitText(i.toChar().toString(), 1)
    }

    override fun onText(text: CharSequence?) {}
    override fun swipeLeft() {}
    override fun swipeRight() {}
    override fun swipeDown() {}
    override fun swipeUp() {}
}