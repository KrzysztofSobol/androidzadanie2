package com.example.android_zadanie2;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class TaskFragment extends Fragment {

    private Task task;
    private EditText nameField;
    private EditText dateField;
    private CheckBox doneCheckBox;
    private Spinner categorySpinner;
    private static String ARG_TASK_ID = "argTaskId";
    private final Calendar calendar = Calendar.getInstance();

    public static TaskFragment newInstance(UUID taskId){
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_TASK_ID, taskId);
        TaskFragment taskFragment = new TaskFragment();
        taskFragment.setArguments(bundle);
        return taskFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            UUID taskId = (UUID) args.getSerializable(ARG_TASK_ID);
            task = TaskStorage.getInstance().getTask(taskId);
        }
    }

    private void setupDateFieldValue(Date date){
        Locale locale = new Locale("pl", "PL");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", locale);
        dateField.setText(dateFormat.format(date));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        nameField = view.findViewById(R.id.task_name);
        dateField = view.findViewById(R.id.task_date);
        doneCheckBox = view.findViewById(R.id.task_done);

        if (task != null) {
            nameField.setText(task.getName());
            nameField.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    task.setName(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });

            DatePickerDialog.OnDateSetListener date  = (view12, year, month, day) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                setupDateFieldValue(calendar.getTime());
                task.setDate(calendar.getTime());
            };

            dateField.setOnClickListener(view1 ->
                    new DatePickerDialog(getContext(), date, calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show());
            setupDateFieldValue(task.getDate());

            doneCheckBox.setChecked(task.isDone());
            doneCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> task.setDone(isChecked));

        } else {
            nameField.setText("");
            dateField.setText("Wybierz datę");
            doneCheckBox.setChecked(false);

            dateField.setOnClickListener(v -> {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePicker = new DatePickerDialog(getContext(), (view1, year, month, dayOfMonth) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(year, month, dayOfMonth);
                    dateField.setText(selectedDate.getTime().toString());
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                datePicker.show();
            });
        }


        categorySpinner = view.findViewById(R.id.task_category);
        categorySpinner.setAdapter(new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, Category.values()));
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { task.setCategory (Category.values() [position]);
            }
            @Override
            public void onNothingSelected (AdapterView<?> parent) {
            }
        });
        categorySpinner.setSelection(task.getCategory().ordinal());

        return view;
    }
}
