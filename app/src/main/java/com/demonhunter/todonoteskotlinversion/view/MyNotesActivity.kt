package com.demonhunter.todonoteskotlinversion.view


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.demonhunter.todonoteskotlinversion.Adapter.NotesAdapter
import com.demonhunter.todonoteskotlinversion.NotesApp
import com.demonhunter.todonoteskotlinversion.R
import com.demonhunter.todonoteskotlinversion.clicklisteners.ItemClickListeners
import com.demonhunter.todonoteskotlinversion.db.Notes
import com.demonhunter.todonoteskotlinversion.utils.AppConstant
import com.demonhunter.todonoteskotlinversion.utils.PrefConst
import com.demonhunter.todonoteskotlinversion.workmanager.MyWorker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import java.util.concurrent.TimeUnit


@Suppress("DEPRECATION")
public class MyNotesActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MyNotesActivity"
        const val ADD_NOTES_CODE = 100
    }

    var fullName: String = ""
    lateinit var fabAddNotes: FloatingActionButton
    lateinit var sharedPreferences: SharedPreferences
    lateinit var recyclerViewNotes: RecyclerView
    var listNotes = ArrayList<Notes>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_notes)
        setupSharedPreference()
        bindViews()
        getIntentData()
        getDataFromDataBase()
        setupToolbarText()
        clickListeners()
        setupRecyclerView()
        setupWorkManager()

    }

    private fun setupWorkManager() {
        val constraint = Constraints.Builder() //for our work to run it must satisfy some conditions
            .build()
        val request = PeriodicWorkRequest
            .Builder(MyWorker::class.java, 1, TimeUnit.MINUTES)
            .setConstraints(constraint)
            .build()                                        //if we want to perform a single task we can add one time one request

        WorkManager.getInstance().enqueue(request)

    }

//    private fun setupDialog() {
//        val view = LayoutInflater.from(this@MyNotesActivity)
//            .inflate(R.layout.add_notes_dialog_layout, null)
//        val ET_title: EditText = view.findViewById(R.id.et_title)
//        val ET_description: EditText = view.findViewById(R.id.et_description)
//        val subBTn: Button = view.findViewById(R.id.btn_submit)
//        val dialog = AlertDialog.Builder(this)
//            .setView(view)
//            .setCancelable(false)
//            .create()
//        subBTn.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(v: View?) {
//                val title=ET_title.text.toString()
//                val description=ET_description.text.toString()
//                val notes=Notes(title=title, description = description)
//                listNotes.add(notes)
//                recyclerViewNotes.adapter?.notifyItemChanged(listNotes.size-1)
//                addNotestoDb(notes)
//                setupRecyclerView()
//                dialog.hide()
//            }
//        })
//        dialog.show()
//    }

    private fun getDataFromDataBase() {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        Log.d(TAG, notesDao.getAll().size.toString())
        listNotes.addAll(notesDao.getAll())

    }

    private fun clickListeners() {

        fabAddNotes.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                startActivityForResult(                                                     //startActivityForResult is used for opening the result on the same activity
                    Intent(this@MyNotesActivity, AddNotesActivity::class.java),
                    ADD_NOTES_CODE)
            }
        })
    }


    private fun setupToolbarText() {
        if (supportActionBar != null) {
            supportActionBar?.title = fullName    //it is null-safe by the use of '?',it will not take null values
        }
    }

    private fun getIntentData() {
        val intent = intent
        if (intent.hasExtra(AppConstant.FULL_NAME)) {
            fullName = intent.getStringExtra(AppConstant.FULL_NAME)!!
        }
        if (fullName.isEmpty()) {
            fullName = sharedPreferences.getString(PrefConst.FULL_NAME, "")!!
        }
    }

    private fun setupSharedPreference() {
        sharedPreferences =
            getSharedPreferences(PrefConst.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    private fun bindViews() {
        fabAddNotes = findViewById(R.id.fabAddNotes)
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes)
    }


    private fun addNotestoDb(notes: Notes) {
        //insertion of notes to db
        val notesApp =
            applicationContext as NotesApp // here we have taken a global context of NotesAppp
        val notesDao = notesApp.getNotesDb().notesDao()
        notesDao.insert(notes)
    }

    private fun setupRecyclerView() {
        val itemClickListeners = object : ItemClickListeners {

            override fun onClick(notes: Notes) {
                val intent = Intent(this@MyNotesActivity, DetailActivity::class.java)
                intent.putExtra(AppConstant.TITLE, notes.title)
                intent.putExtra(AppConstant.TITLE, notes.description)
                startActivity(intent)
            }

            override fun onUpdate(notes: Notes) {
                Log.d(TAG, notes.isTaskCompleted.toString())
                val notesApp = applicationContext as NotesApp
                val notesDao = notesApp.getNotesDb().notesDao()
                notesDao.updateNotes(notes)

            }
        }
        val notesAdapter = NotesAdapter(listNotes, itemClickListeners)
        val linearLayoutManager = LinearLayoutManager(this@MyNotesActivity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewNotes.layoutManager = linearLayoutManager
        recyclerViewNotes.adapter = notesAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_NOTES_CODE && resultCode==Activity.RESULT_OK) {
            val title = data?.getStringExtra(AppConstant.TITLE)
            val description = data?.getStringExtra(AppConstant.DESCRIPTION)
            val imagePath = data?.getStringExtra(AppConstant.IMAGE_PATH)
            val notes = Notes(
                title = title!!,
                description = description!!,
                imagePath = imagePath!!,
                isTaskCompleted = false
            )
            addNotestoDb(notes)
            listNotes.add(notes)
            recyclerViewNotes.adapter?.notifyItemChanged(listNotes.size - 1) //its for updating the data in the recycler view

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.blog) {
            Log.d(TAG, "Click Successful")
            val intent = Intent(this@MyNotesActivity, BlogActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}




