package com.haeun.diet_memo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import java.lang.Exception

class SplashActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    @OptIn(UnstableApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        auth = Firebase.auth

        try { // 현재 로그인이 되어있으면
            Log.d("SPLASH", auth.currentUser!!.uid) // 현재 로그인이 되어있으면
            Toast.makeText(this, "원래 비회원 로그인이 되어있는 사람입니다!", Toast.LENGTH_LONG).show() // text 뜸
            Handler().postDelayed({ // 3000ms 이후 화면이 넘어감
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 3000)

        } catch (e : Exception) { // 현재 로그인이 되어있지 않으면
            Log.d("SPLASH", "회원가입 시켜줘야함") // text 뜸

            auth.signInAnonymously() // 회원가입 시킴
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "비회원 로그인 성공", Toast.LENGTH_LONG).show() // 회원가입 되면 성공이라고 text 뜸

                        Handler().postDelayed({
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }, 3000)

                    } else {
                        Toast.makeText(this, "비회원 로그인 실패", Toast.LENGTH_LONG).show() // 회원가입 안되면 실패라고 text 뜸
                    }
                }
        }

    }
}