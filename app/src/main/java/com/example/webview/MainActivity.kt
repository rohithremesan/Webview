package com.example.webview

import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.webview.databinding.ActivityMainBinding

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

        binding.web.apply {
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)

                    if (url != null && (url.contains("facebook") || url.contains("instagram") || url.contains("x.com"))) {
                        view?.evaluateJavascript("""
               (function() {
    let menu = document.createElement('div');
    menu.innerHTML = '☰';
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
    document.body.appendChild(menu);
})();

            """.trimIndent(), null)
                    }
                }


            }

            settings.javaScriptEnabled = true
            settings.domStorageEnabled=true
            loadUrl("https://www.whatsapp.com/")
        }

    }
}