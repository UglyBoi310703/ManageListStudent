package com.example.manageliststudent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.manageliststudent.R

class MainActivity : AppCompatActivity() {
    private lateinit var etName: EditText
    private lateinit var etId: EditText
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
        etName = findViewById(R.id.etName)
        etId = findViewById(R.id.etId)
        btnAdd = findViewById(R.id.btnAdd)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
        lvStudents = findViewById(R.id.lvStudents)

        // Thiết lập adapter cho ListView
        adapter = StudentAdapter(this, studentList)
        lvStudents.adapter = adapter

        // Xử lý sự kiện khi chọn một sinh viên trong ListView
        lvStudents.setOnItemClickListener { _, _, position, _ ->
            selectedPosition = position
            val student = studentList[position]
            etName.setText(student.name)
            etId.setText(student.id)
        }

        // Xử lý nút Add
        btnAdd.setOnClickListener {
            val name = etName.text.toString().trim()
            val id = etId.text.toString().trim()

            if (name.isEmpty() || id.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Kiểm tra MSSV đã tồn tại
            if (studentList.any { it.id == id }) {
                Toast.makeText(this, "MSSV đã tồn tại", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            studentList.add(Student(id, name))
            adapter.notifyDataSetChanged()
            clearInputs()
            Toast.makeText(this, "Thêm sinh viên thành công", Toast.LENGTH_SHORT).show()
        }

        // Xử lý nút Update
        btnUpdate.setOnClickListener {
            if (selectedPosition == -1) {
                Toast.makeText(this, "Vui lòng chọn sinh viên để cập nhật", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val name = etName.text.toString().trim()
            val id = etId.text.toString().trim()

            if (name.isEmpty() || id.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Kiểm tra MSSV đã tồn tại (trừ sinh viên đang được chọn)
            if (studentList.any { it.id == id && it != studentList[selectedPosition] }) {
                Toast.makeText(this, "MSSV đã tồn tại", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            studentList[selectedPosition] = Student(id, name)
            adapter.notifyDataSetChanged()
            clearInputs()
            selectedPosition = -1
            Toast.makeText(this, "Cập nhật sinh viên thành công", Toast.LENGTH_SHORT).show()
        }

        // Xử lý nút Delete
        btnDelete.setOnClickListener {
            if (selectedPosition == -1) {
                Toast.makeText(this, "Vui lòng chọn sinh viên để xóa", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            studentList.removeAt(selectedPosition)
            adapter.notifyDataSetChanged()
            clearInputs()
            selectedPosition = -1
            Toast.makeText(this, "Xóa sinh viên thành công", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearInputs() {
        etName.text.clear()
        etId.text.clear()
    }
}