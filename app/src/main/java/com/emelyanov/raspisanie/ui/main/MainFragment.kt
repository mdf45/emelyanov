package com.emelyanov.raspisanie.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emelyanov.raspisanie.R
import com.emelyanov.raspisanie.ui.Item
import com.emelyanov.raspisanie.ui.MyAdapter
import com.emelyanov.raspisanie.ui.SQLiteHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*


class MainFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel

    @SuppressLint("SimpleDateFormat")
    private val sdf = SimpleDateFormat("dd.MM.yyyy")
    private val calendar = Calendar.getInstance()
    private var date = sdf.format(calendar.time)

    private lateinit var leftButton: FloatingActionButton
    private lateinit var rightButton: FloatingActionButton
    private lateinit var dateText: TextView
    private lateinit var itemRv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_main, container, false)

        leftButton = root.findViewById(R.id.left_Button)
        rightButton = root.findViewById(R.id.right_Button)
        dateText = root.findViewById(R.id.date_TextView)
        itemRv = root.findViewById(R.id.items_rv)

        itemRv.setHasFixedSize(true)

        val llm = LinearLayoutManager(context)
        itemRv.layoutManager = llm

        upload()

        leftButton.setOnClickListener { prev() }
        rightButton.setOnClickListener { next() }

        return root
    }

    private fun next() {
        calendar.add(Calendar.DATE, 1)
        date = sdf.format(calendar.time)

        upload()
    }

    private fun prev() {
        calendar.add(Calendar.DATE, -1)
        date = sdf.format(calendar.time)

        upload()
    }

    private fun upload() {
        val adapter = MyAdapter(Item.initialize(context, date))
        itemRv.adapter = adapter

        dateText.text = date

        leftButton.isEnabled = calendar.time.date != Calendar.getInstance().time.date
    }
}