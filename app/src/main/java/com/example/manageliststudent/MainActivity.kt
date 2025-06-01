package com.example.manageliststudent
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.manageliststudent.R

class MainActivity : AppCompatActivity() {
    private lateinit var btnAdd: Button
    private lateinit var lvStudents: ListView
    private val studentList = mutableListOf<Student>()
    private lateinit var adapter: StudentAdapter
    private var selectedPosition: Int = -1

    private val addEditStudentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val id = data?.getStringExtra("ID")
            val name = data?.getStringExtra("NAME")
            val position = data?.getIntExtra("POSITION", -1)

            if (id != null && name != null) {
                if (position == -1) {
                    // Thêm sinh viên mới
                    if (studentList.any { it.id == id }) {
                        Toast.makeText(this, "Student ID already exists", Toast.LENGTH_SHORT).show()
                    } else {
                        studentList.add(Student(id, name))
                        adapter.notifyDataSetChanged()
                        Toast.makeText(this, "Student added successfully", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Cập nhật sinh viên
                    if (studentList.any { it.id == id && it != studentList[position!!] }) {
                        Toast.makeText(this, "Student ID already exists", Toast.LENGTH_SHORT).show()
                    } else {
                        studentList[position!!] = Student(id, name)
                        adapter.notifyDataSetChanged()
                        selectedPosition = -1
                        Toast.makeText(this, "Student updated successfully", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.danhsach)

        // Khởi tạo các thành phần giao diện
        btnAdd = findViewById(R.id.btnAdd)
        lvStudents = findViewById(R.id.lvStudents)

        // Thiết lập adapter cho ListView
        adapter = StudentAdapter(this, studentList) { position ->
            // Xử lý sự kiện khi nhấn nút Edit
            val intent = Intent(this, AddEditStudentActivity::class.java)
            intent.putExtra("STUDENT", studentList[position])
            intent.putExtra("POSITION", position)
            addEditStudentLauncher.launch(intent)
        }
        lvStudents.adapter = adapter

        // Handle item click to select a student
        lvStudents.setOnItemClickListener { _, _, position, _ ->
            selectedPosition = position
            Toast.makeText(this, "Selected: ${studentList[position].name}", Toast.LENGTH_SHORT).show()
        }

        // Handle Add button
        btnAdd.setOnClickListener {
            val intent = Intent(this, AddEditStudentActivity::class.java)
            addEditStudentLauncher.launch(intent)
        }
    }
}
