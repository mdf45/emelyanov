package com.emelyanov.raspisanie.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.emelyanov.raspisanie.R
import com.emelyanov.raspisanie.ui.edit.EditFragment

class MyDialogFragment(private var fragment: EditFragment): DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog, container, false)
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog!!.findViewById<Button>(R.id.add_b).setOnClickListener {
            update()
            dismiss()
        }
    }

    private fun update() {
        val names= view?.findViewById<EditText>(R.id.name_dialog)?.text.toString()
        val times= view?.findViewById<EditText>(R.id.time_dialog)?.text.toString()

        fragment.sendData(names, times)
    }
}