package com.example.manageliststudent

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.manageliststudent.R

class MainActivity : AppCompatActivity() {
    private lateinit var btnAdd: Button
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    private lateinit var lvStudents: ListView
    private val studentList = mutableListOf<Student>()
    private lateinit var adapter: StudentAdapter
    private var selectedPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.danhsach)

        // Khởi tạo các thành phần giao diện
        btnAdd = findViewById(R.id.btnAdd)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
        lvStudents = findViewById(R.id.lvStudents)

        // Thiết lập adapter cho ListView
        adapter = StudentAdapter(this, studentList)
        lvStudents.adapter = adapter

        // Handle item click to select a student
        lvStudents.setOnItemClickListener { _, _, position, _ ->
            selectedPosition = position
            Toast.makeText(this, "Selected: ${studentList[position].name}", Toast.LENGTH_SHORT).show()
        }

        // Handle Add button
        btnAdd.setOnClickListener {
            showAddStudentDialog()
        }

        // Handle Update button
        btnUpdate.setOnClickListener {
            if (selectedPosition == -1) {
                Toast.makeText(this, "Please select a student to update", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            showUpdateStudentDialog()
        }

        // Handle Delete button
        btnDelete.setOnClickListener {
            if (selectedPosition == -1) {
                Toast.makeText(this, "Please select a student to delete", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            showDeleteConfirmationDialog()
        }
    }

    private fun showAddStudentDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_student_input, null)
        val etName = dialogView.findViewById<EditText>(R.id.etDialogName)
        val etId = dialogView.findViewById<EditText>(R.id.etDialogId)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Add Student")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val name = etName.text.toString().trim()
                val id = etId.text.toString().trim()

                if (name.isEmpty() || id.isEmpty()) {
                    Toast.makeText(this, "Please enter all information", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                if (studentList.any { it.id == id }) {
                    Toast.makeText(this, "Student ID already exists", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                studentList.add(Student(id, name))
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "Student added successfully", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

    private fun showUpdateStudentDialog() {
        val student = studentList[selectedPosition]
        val dialogView = layoutInflater.inflate(R.layout.dialog_student_input, null)
        val etName = dialogView.findViewById<EditText>(R.id.etDialogName)
        val etId = dialogView.findViewById<EditText>(R.id.etDialogId)

        etName.setText(student.name)
        etId.setText(student.id)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Update Student")
            .setView(dialogView)
            .setPositiveButton("Update") { _, _ ->
                val name = etName.text.toString().trim()
                val id = etId.text.toString().trim()

                if (name.isEmpty() || id.isEmpty()) {
                    Toast.makeText(this, "Please enter all information", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                if (studentList.any { it.id == id && it != student }) {
                    Toast.makeText(this, "Student ID already exists", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                studentList[selectedPosition] = Student(id, name)
                adapter.notifyDataSetChanged()
                selectedPosition = -1
                Toast.makeText(this, "Student updated successfully", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

    private fun showDeleteConfirmationDialog() {
        val student = studentList[selectedPosition]
        AlertDialog.Builder(this)
            .setTitle("Delete Student")
            .setMessage("Are you sure you want to delete ${student.name}?")
            .setPositiveButton("Delete") { _, _ ->
                studentList.removeAt(selectedPosition)
                adapter.notifyDataSetChanged()
                selectedPosition = -1
                Toast.makeText(this, "Student deleted successfully", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}