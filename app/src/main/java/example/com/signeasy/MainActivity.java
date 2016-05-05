package example.com.signeasy;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import example.com.signeasy.adapter.CustomPagerAdapter;
import example.com.signeasy.model.LoginErrorResponse;
import example.com.signeasy.model.LoginSuccessResponse;
import example.com.signeasy.utils.GSONUtils;

public class MainActivity extends AppCompatActivity {

    Button signInButton;
    public static final String CORRECT_PASSWORD = "vader";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        signInButton = (Button)findViewById(R.id.signin);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String username = "darthvader@starwars.com";
                    String password = CORRECT_PASSWORD;//Replace with some thing else for error scenario

                    password = getSHA256Hash(Base64.encodeToString(password.getBytes(), Base64.NO_WRAP));

                    String source = username + ":" + password;
                    final String b64encoded = "Basic " + Base64.encodeToString(source.getBytes(), Base64.NO_WRAP);

                    Response.ErrorListener errorListener = new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            LoginErrorResponse response = GSONUtils.getInstance().getGson().fromJson(new String(error.networkResponse.data), LoginErrorResponse.class);
                            Toast.makeText(getApplicationContext(), "Error :"+response.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    };

                    Request<LoginSuccessResponse> loginRequest = new Request<LoginSuccessResponse>(
                            Request.Method.POST, "https://v4-dev.getsigneasy.com/v4/token", errorListener) {
                        @Override
                        protected Response<LoginSuccessResponse> parseNetworkResponse(NetworkResponse networkResponse) {

                            if (networkResponse != null && networkResponse.data != null) {
                                try  {
                                    if (networkResponse.statusCode / 100 != 2) {
                                        return Response.error(new VolleyError(networkResponse));
                                    }
                                    LoginSuccessResponse response = GSONUtils.getInstance().getGson().fromJson(new String(networkResponse.data), LoginSuccessResponse.class);
                                    return Response.success(response, null);
                                } catch (Throwable ignore) {
                                }
                            }
                            return Response.error(new VolleyError(networkResponse));
                        }

                        @Override
                        protected void deliverResponse(LoginSuccessResponse response) {
                            try {
                                Toast.makeText(getApplicationContext(), "Login Success!", Toast.LENGTH_LONG).show();
                            } catch (Throwable ignore) {
                            }
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> header = new HashMap<>();
                            header.put("Authorization", b64encoded);
                            return header;
                        }
                    };

                    Volley.newRequestQueue(MainActivity.this).add(loginRequest);
                } catch (Exception ignore) {
                }
            }
        });
    }

    private String getSHA256Hash(String password) throws Exception{

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
        final byte[] digest = md.digest();

        StringBuilder sb = new StringBuilder();
        for (byte aDigest : digest) {
            sb.append(Integer.toString((aDigest & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
