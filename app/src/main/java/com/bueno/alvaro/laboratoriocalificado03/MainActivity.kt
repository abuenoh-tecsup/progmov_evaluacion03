package com.bueno.alvaro.laboratoriocalificado03

import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bueno.alvaro.laboratoriocalificado03.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val teacherAdapter by lazy { TeacherAdapter(emptyList()) }
    private val viewModel by lazy { MainViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spanCount = calculateSpanCount()
        binding.rvTeachers.layoutManager = GridLayoutManager(this, spanCount)
        binding.rvTeachers.adapter = teacherAdapter

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(this, Observer { isLoading ->
            binding.progressBar.isVisible = isLoading
        })

        viewModel.teacherList.observe(this, Observer { teachers ->
            teacherAdapter.list = teachers
            teacherAdapter.notifyDataSetChanged()
        })

        viewModel.errorApi.observe(this, Observer { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        })
    }

    private fun calculateSpanCount(): Int {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density

        return when {
            screenWidthDp >= 900 -> 3
            screenWidthDp >= 600 -> 2
            else -> 1
        }
    }
}
