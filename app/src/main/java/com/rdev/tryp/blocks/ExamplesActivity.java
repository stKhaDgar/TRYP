package com.rdev.tryp.blocks;

import android.os.Bundle;

import com.rdev.tryp.ContentActivity;
import com.rdev.tryp.R;

import androidx.appcompat.app.AppCompatActivity;

public class ExamplesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        findViewById(R.id.recapButton).setOnClickListener(v -> startActivity(ContentActivity.createIntent(ExamplesActivity.this, ContentActivity.TYPE_RECAP)));
        findViewById(R.id.legalButton).setOnClickListener(v -> startActivity(ContentActivity.createIntent(ExamplesActivity.this, ContentActivity.TYPE_LEGAL)));
        findViewById(R.id.invite1Button).setOnClickListener(v -> startActivity(ContentActivity.createIntent(ExamplesActivity.this, ContentActivity.TYPE_INVITE1)));
        findViewById(R.id.invite2Button).setOnClickListener(v -> startActivity(ContentActivity.createIntent(ExamplesActivity.this, ContentActivity.TYPE_INVITE2)));
        findViewById(R.id.invite3Button).setOnClickListener(v -> startActivity(ContentActivity.createIntent(ExamplesActivity.this, ContentActivity.TYPE_INVITE3)));
        findViewById(R.id.helpButton).setOnClickListener(v -> startActivity(ContentActivity.createIntent(ExamplesActivity.this, ContentActivity.TYPE_HELP)));
        findViewById(R.id.notificationsButton).setOnClickListener(v -> startActivity(ContentActivity.createIntent(ExamplesActivity.this, ContentActivity.TYPE_NOTIFICATIONS)));
    }
}