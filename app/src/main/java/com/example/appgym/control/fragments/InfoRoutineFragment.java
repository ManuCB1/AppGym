package com.example.appgym.control.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.appgym.R;
import com.example.appgym.adapter.RecyclerInfoRoutineAdapter;
import com.example.appgym.model.PopupListener;
import com.example.appgym.model.Rutina;
import com.example.appgym.model.RutinaViewModel;
import com.example.appgym.model.TaskCompleted;
import com.example.appgym.service.RutinaServiceImpl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class InfoRoutineFragment extends Fragment implements PopupListener {

    private RecyclerView recycler;
    private RecyclerInfoRoutineAdapter adapter;
    private List<Rutina> rutinasAll;
    private RutinaViewModel viewModel;
    private RutinaServiceImpl rutinaService;
    private List<Rutina> rutinas = new ArrayList<>();
    private static final String argParam1 = "rutina";

    public InfoRoutineFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_routine, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recycler = view.findViewById(R.id.recyclerInfoRoutine);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
//        viewModel = new ViewModelProvider(this).get(RutinaViewModel.class);
//        viewModel = new RutinaViewModel(getContext());
        rutinaService = new RutinaServiceImpl(getContext());

        try {
            getRutinas();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }

    private void getRutinas() throws UnsupportedEncodingException {
        rutinaService.getRutinas(new TaskCompleted<List<Rutina>>() {
            @Override
            public void onTaskCompleted(List<Rutina> s) {
                rutinas = s;
                loadRecycler();
            }
        });
    }

    private void loadRecycler() {
        adapter = new RecyclerInfoRoutineAdapter(rutinas, this);
        recycler.setAdapter(adapter);
    }

    @Override
    public void selectedItem(int position, MenuItem item) {
        if (item.getItemId() == R.id.titleInfo){
            sendRoutine(position);
        }
        if (item.getItemId() == R.id.titleRemove){

        }
    }

    private void sendRoutine(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(argParam1, rutinas.get(position));
        Navigation.findNavController(getView()).navigate(R.id.action_infoRoutineFragment_to_detailFragment, bundle);
    }

//    private void infoMessage(int position) {
//        Rutina rutina = rutinas.get(position);
//        List<Ejercicio> ejercicios = rutina.getEjercicios();
//        String message = "";
//        for (Ejercicio ejercicio: ejercicios){
//            message = message+"\nEjercicio: "+ejercicio.getNombre()+
//                    "\nSeries"+ ejercicio.getSeries() +
//                    "\nRepeticiones";
//            for (String repeticion : ejercicio.getRepeticiones().split(",")) {
//                message = message + "\n" + repeticion;
//            }
//
//        }
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setTitle("Información")
//                .setMessage("Nombre Rutina"+ rutina.getNombre()
//                            + "\nEjercicios" + message
//                )
//                .setPositiveButton("Aceptar", (dialog, i) -> {
//                    dialog.dismiss();
//                })
//                .create()
//                .show();
//    }
}