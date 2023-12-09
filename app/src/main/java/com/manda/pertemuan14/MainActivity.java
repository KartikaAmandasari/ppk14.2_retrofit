package com.manda.pertemuan14;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private BookApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiService = ApiClient.getRetrofitInstance().create(BookApiService.class);

        // Contoh panggilan API
        getBooks();
    }

    private void getBooks() {
        Call<List<Book>> call = apiService.getBooks();
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(@NonNull Call<List<Book>> call, @Nullable Response<List<Book>> response) {
                if (response != null && response.isSuccessful()) {
                    List<Book> books = response.body();
                    if (books != null) {
                        for (Book book : books) {
                            Log.d(TAG, "Title: " + book.getTitle() +
                                    ", Author: " + book.getAuthor());
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Book>> call, @Nullable Throwable t) {
                Log.e(TAG, "Error fetching books", t);
            }
        });
    }
}
