package edu.ntnu

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), ListFragment.ItemSelectedListener {

    private val items = listOf(
        Item("Tenet", R.drawable.image6, "A protagonist is recruited by an organization called Tenet and is tasked with preventing World War III through the manipulation of time. Armed with the ability to invert time, he embarks on a mission to stop a Russian oligarch from triggering a global catastrophe."),
        Item("Inception", R.drawable.image1, "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster."),
        Item("Interstellar", R.drawable.image2, "When Earth becomes uninhabitable in the future, a farmer and ex-NASA pilot, Joseph Cooper, is tasked to pilot a spacecraft, along with a team of researchers, to find a new planet for humans."),
        Item("Dunkirk", R.drawable.image4, "Allied soldiers from Belgium, the British Empire, and France are surrounded by the German Army and evacuated during a fierce battle in World War II."),
        Item("Oppenheimer", R.drawable.image5, "A look at the life of J. Robert Oppenheimer, the physicist tasked with developing the first nuclear weapon, as he grapples with his moral conscience while designing an instrument of destruction."),
    )
    private var selectedItemIndex = 0
    private lateinit var detailFragment: DetailFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val existingFragment = supportFragmentManager.findFragmentById(R.id.detail_container)

        if (existingFragment is DetailFragment) {
            detailFragment = existingFragment
        } else {
            detailFragment = DetailFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.list_container, ListFragment())
                .add(R.id.detail_container, detailFragment)
                .commit()
        }
    }


    override fun getItemList(): List<Item> {
        return items
    }

    override fun onItemSelected(item: Item) {
        detailFragment.showItemDetail(item)
        selectedItemIndex = items.indexOf(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_prev -> {
                if (selectedItemIndex > 0) {
                    selectedItemIndex--
                    detailFragment.showItemDetail(items[selectedItemIndex])
                }
            }
            R.id.action_next -> {
                if (selectedItemIndex < items.size - 1) {
                    selectedItemIndex++
                    detailFragment.showItemDetail(items[selectedItemIndex])
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}