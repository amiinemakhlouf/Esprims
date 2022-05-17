package esprims.gi2.esprims

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CallLog.Calls.PRIORITY
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import esprims.gi2.esprims.databinding.ActivityMainBinding
import esprims.gi2.esprims.databinding.HeaderNavigationDrawerBinding
class MainActivity : AppCompatActivity() {
    var gradeUID: Int? = null
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    var emlpoiEx: String? = null
    var emploi: String? = null
    var shouldFetch = true
    var fromService=false
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        Log.d(" testitfromcreate",this.toString())
        // return to APP style after splash screen
        setTheme(R.style.Theme_Esprims)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()


        binding.toolbae.setNavigationOnClickListener {
            val drawer = binding.drawerLayout
            drawer.open()

        }



        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.dashbordFragment,
                R.id.loginFragment,
                R.id.emlpoiExamenFragment,
                R.id.noteFragment,
                R.id.payementFragment,
                R.id.actualityFragment,
                R.id.coursFragment

            ),

            binding.drawerLayout
        )
       navController.addOnDestinationChangedListener { controller, destination, arguments ->
            destination.label = when (destination.id) {
                R.id.dashbordFragment -> resources.getString(R.string.app_name)
                R.id.emlpoiExamenFragment -> "emploi d'examen"
                R.id.noteFragment -> "note et absences"
                R.id.payementFragment -> "payement"
                R.id.actualityFragment-> "les actualités"
                R.id.coursFragment ->"cours "
                else -> resources.getString(R.string.app_name)
            }
        }
        setSupportActionBar(binding.toolbae)
        binding.navView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)


        binding.navView.menu.findItem(R.id.logOut).setOnMenuItemClickListener {

            Firebase.auth.signOut()
            startActivity(Intent.makeRestartActivityTask(this.intent?.component))


            true
        }

        Intent(this,MyService::class.java).putExtra(
            "gradeID",gradeUID)
        .also {
            startService(it)
        }


    }


    //link toolbar with navigation view
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                ||
                super.onSupportNavigateUp()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d("testitmenitent","yes i'm here on new intent ")

        if (intent?.getStringExtra("origin")=="service"){
            fromService=true



            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController = navHostFragment.findNavController()

        }
        else{
            Log.d("testit","no not here")

        }


    }








}