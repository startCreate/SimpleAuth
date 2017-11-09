package demo.simpleauth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jaychang.sa.SimpleAuth;
import com.jaychang.sa.SocialUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity {

    private static final String EXTRA_USER = "EXTRA_USER";
    private static final String EXTRA_TYPE = "EXTRA_TYPE";
    @BindView(R.id.userView)
    TextView userView;
    @BindView(R.id.disconnectButton)
    Button disconnectButton;
    @BindView(R.id.revokeButton)
    Button revokeButton;
    private String type;

    public static void start(Context context, String type, SocialUser socialUser) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(EXTRA_USER, socialUser);
        intent.putExtra(EXTRA_TYPE, type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        SocialUser socialUser = getIntent().getParcelableExtra(EXTRA_USER);

        userView.setText(socialUser.toString());

        type = getIntent().getStringExtra(EXTRA_TYPE);

        handleVisibility();
    }

    @OnClick(R.id.disconnectButton)
    void disconnect() {
        SimpleAuth.getInstance().disconnectTwitter();
        finish();
    }

    @OnClick(R.id.revokeButton)
    void revoke() {
        finish();
    }

    private void handleVisibility() {
        if (MainActivity.TWITTER.equals(type)) {
            revokeButton.setVisibility(View.GONE);
        }
    }
}
