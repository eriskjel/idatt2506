package edu.ntnu

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import edu.ntnu.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val firstName = "Erik"
    private val lastName = "Skjellevik"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        menu.add(firstName)
        menu.add(lastName)
        Log.d("Menu", "Menu created")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        when (item.title.toString()) {
            firstName -> Log.w("Menyvalg", item.title.toString())
            lastName -> Log.e("Menyvalg", item.title.toString())
        }
        return true
    }
}