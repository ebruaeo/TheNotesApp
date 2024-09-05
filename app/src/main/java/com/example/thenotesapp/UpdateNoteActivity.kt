package com.example.thenotesapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.thenotesapp.databinding.ActivityUpdateNoteBinding

class UpdateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateNoteBinding
    private lateinit var db: NotesDataBaseHelper
    private var noteId: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = NotesDataBaseHelper(this)

        noteId = intent.getIntExtra("note_id", -1)
        if (noteId == -1) {
            finish()
            return
        }

        val note = db.getNoteByID(noteId)
        binding.updateTitleEditText.setText(note.title)
        binding.updateContentEditText.setText(note.content)

        binding.updateSaveButton.setOnClickListener {
            val newTitle = binding.updateTitleEditText.text.toString()
            val newContent = binding.updateContentEditText.text.toString()
            val updatedNote = Note(noteId,newTitle,newContent)
            db.updateNote(updatedNote)
            finish()
            Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show()
        }
    }
}