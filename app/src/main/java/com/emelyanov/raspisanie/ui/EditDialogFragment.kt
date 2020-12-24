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

class EditDialogFragment(private var fragment: EditFragment, private var name: String, private var time: String): DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.edit_dialog, container, false)
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)

        view?.findViewById<EditText>(R.id.name_dialog_edit)?.setText(name)
        view?.findViewById<EditText>(R.id.time_dialog_edit)?.setText(time)

        dialog!!.findViewById<Button>(R.id.add_b_edit).setOnClickListener {
            update()
            dismiss()
        }
        dialog!!.findViewById<Button>(R.id.delete_b_edit).setOnClickListener {
            delete()
            dismiss()
        }
    }

    private fun update() {

        val names= "$name;${view?.findViewById<EditText>(R.id.name_dialog_edit)?.text.toString()}"
        val times= "$time;${view?.findViewById<EditText>(R.id.time_dialog_edit)?.text.toString()}"

        fragment.sendDataEdit(names, times)
    }

    private fun delete() {
        fragment.deleteData(name, time)
    }
}