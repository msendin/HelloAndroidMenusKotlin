package udl.eps.demos.helloandroidmenuskotlin

import android.os.Bundle
import android.view.*
import android.view.ContextMenu.ContextMenuInfo
import android.view.View.OnLongClickListener
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import udl.eps.demos.helloandroidmenuskotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnLongClickListener, ActionMode.Callback {
    private var mActionMode: ActionMode? = null
    var act: ActionBar? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // CHOOSE THE TYPE OF CONTEXTUAL MENU YOU WANT (Contextual or v)
        //registerForContextMenu(binding.textview1);
        binding.textview1.setOnLongClickListener(this)

        act = supportActionBar
    }

    override fun onLongClick(view: View): Boolean {
        if (mActionMode != null) return false
        // Start the CAB using the ActionMode.Callback defined above
        mActionMode = this.startActionMode(this)
        view.isSelected = true
        return true
    }


    // Called when the action mode is created; startActionMode() was called
    override fun onCreateActionMode(mode: ActionMode, menu: Menu?): Boolean {
        // Inflate a menu resource providing context menu items
        val inflater = mode.menuInflater
        inflater.inflate(R.menu.context_menu, menu)
        return true
    }

    // Called each time the action mode is shown. Always called after
    // onCreateActionMode,
    // but may be called multiple times if the mode is invalidated.
    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return false // Return false if nothing is done
    }

    // Called when the user selects a contextual menu item
    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_actionM_op1 -> {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                mode.finish() // Action picked, so close the CAB
                true
            }
            R.id.menu_actionM_op2 -> {
                act!!.title = getString(R.string.nwMen)
                act!!.subtitle = getString(R.string.actBar)
                mode.finish() // Action picked, so close the CAB
                true
            }
            else -> false
        }
    }

    // Called when the user exits the action mode
    override fun onDestroyActionMode(mode: ActionMode?) {
        mActionMode = null
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        menu.findItem(R.id.menu_opSearch).actionView
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val s: String
        if (item.itemId == R.id.menu_opSearch) {
            //v = item.getActionView();
            s = getString(R.string.collapsible) + getString(R.string.pulsada)
            binding.textview1.text = s
        } else {
            s = getString(R.string.opcMenu) + item.title.toString() + getString(R.string.orden) + item.order
            binding.textview1.text = s
        }
        return true
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?, v: View?,
        menuInfo: ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_ctx_etiqueta, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val s: String
        return if (item.itemId == R.id.menu_cont_op1 || item.itemId == R.id.menu_cont_op2) {
            s = getString(R.string.etqOpc) + item.order + getString(R.string.pulsada)
            binding.textview1.text = s
            true
        } else super.onContextItemSelected(item)
    }
}