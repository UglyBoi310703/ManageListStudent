package com.example.manageliststudent

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.manageliststudent.R

class AddEditStudentActivity : AppCompatActivity() {
    private lateinit var etName: EditText
    private lateinit var etId: EditText
    private lateinit var btnOk: Button
    private lateinit var btnCancel: Button
    private var student: Student? = null
    private var position: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_student)

        // Khởi tạo các thành phần giao diện
        etName = findViewById(R.id.etDialogName)
        etId = findViewById(R.id.etDialogId)
        btnOk = findViewById(R.id.btnOk)
        btnCancel = findViewById(R.id.btnCancel)

        // Kiểm tra xem là thêm hay chỉnh sửa
        student = intent.getParcelableExtra("STUDENT", Student::class.java)
        position = intent.getIntExtra("POSITION", -1)

        if (student != null) {
            // Chế độ chỉnh sửa
            etName.setText(student?.name)
            etId.setText(student?.id)
            title = "Edit Student"
        } else {
            // Chế độ thêm
            title = "Add Student"
        }

        // Xử lý nút OK
        btnOk.setOnClickListener {
            val name = etName.text.toString().trim()
            val id = etId.text.toString().trim()

            if (name.isEmpty() || id.isEmpty()) {
                Toast.makeText(this, "Please enter all information", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val resultIntent = Intent()
            resultIntent.putExtra("ID", id)
            resultIntent.putExtra("NAME", name)
            resultIntent.putExtra("POSITION", position)
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        // Xử lý nút Cancel
        btnCancel.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}
