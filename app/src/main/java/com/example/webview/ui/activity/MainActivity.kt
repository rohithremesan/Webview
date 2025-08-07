package com.example.webview.ui.activity

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.webview.R
import com.example.webview.databinding.ActivityMainBinding
import com.example.webview.ui.fragment.BottomMenuFragment
import com.example.webview.ui.utility.WebAppInterface

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initalizeWebView()
        clickListner()



    }

    private fun clickListner() {
        binding.menuBtn.setOnClickListener {
            val bottomsheet = BottomMenuFragment()
            bottomsheet.show(supportFragmentManager,bottomsheet.tag)
        }
    }

    private fun initalizeWebView() {
        binding.web.apply {
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    binding.progressBar.visibility= View.GONE

                    if (url != null && (url.contains("facebook") || url.contains("instagram") || url.contains("x.com"))) {
                        view?.evaluateJavascript("""
               (function() {
                console.log('injected from Android');
                document.documentElement.style.backgroundColor = '#e6f7ff';
                document.body.style.backgroundColor = '#e6f7ff';

                
               if (document.getElementById('injectedMenu')) return;
               
    let menu = document.createElement('div');
    menu.innerHTML = '☰';
    menu.id = 'injectedMenu';
    menu.style.position = 'fixed';
    menu.style.bottom = '10px';
    menu.style.left = '30px';
    menu.style.width = '56px'; 
    menu.style.height = '56px'; 
    menu.style.fontSize = '24px';
    menu.style.zIndex = '9999';
    menu.style.color = '#fff';
    menu.style.background = '#2768F5';
    menu.style.borderRadius = '50%'; 
    menu.style.boxShadow = '0 2px 8px rgba(0,0,0,0.3)';
    menu.style.display = 'flex';
    menu.style.alignItems = 'center';
    menu.style.justifyContent = 'center';
    menu.onclick = function() {
        AndroidInterface.onMenuClicked();
    }
    document.body.appendChild(menu);
    
    
})();

            """.trimIndent(), null)
                    }
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    binding.progressBar.visibility= View.VISIBLE
                }


                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    binding.web.loadData(
                        "<html><body><center><h2>Something went wrong.</h2><p>${error?.description}</p></center></body></html>",
                        "text/html",
                        "UTF-8"
                    )
                }


            }
            addJavascriptInterface(WebAppInterface(this@MainActivity, onClick = {
                val bottomsheet = BottomMenuFragment()
                bottomsheet.show(supportFragmentManager,bottomsheet.tag)
            }),"AndroidInterface")
            settings.javaScriptEnabled = true
            settings.domStorageEnabled=true
            loadUrl("https://www.whatsapp.com/")
        }
    }

    override fun onBackPressed() {
        if (binding.web.canGoBack()) {
            binding.web.goBack()
        } else {
            super.onBackPressed()
        }
    }
}

