package com.example.appgym.control.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.appgym.R;
import com.example.appgym.adapter.RecyclerHistorialAdapter;
import com.example.appgym.model.Rutina;
import com.example.appgym.model.TaskCompleted;
import com.example.appgym.repository.RutinaRepositoryImpl;
import com.example.appgym.utils.Constantes;
import com.example.appgym.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class HistorialFragment extends BaseFragment {

    private Rutina rutina;
    private RutinaRepositoryImpl rutinaRepository;
    private List<Rutina> historial;
    private RecyclerView recycler;
    private RecyclerHistorialAdapter historialAdapter;
    private int title = R.string.title_historial;
    private int menu = 0;
    private static final String argParam1 = Constantes.ARG_RUTINA;
    public HistorialFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            rutina = (Rutina) getArguments().getSerializable(argParam1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_historial, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recycler = view.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));

        setMenu(getString(title), menu);
        setData();
    }

    private void setData() {
        rutinaRepository = new RutinaRepositoryImpl(requireContext());
        historial = new ArrayList<>();
        rutinaRepository.getHistorialByRutina(rutina.getId(), new TaskCompleted<List<Rutina>>() {
            @Override
            public void onTaskCompleted(List<Rutina> s) {
                if (s != null){
                    loadRecycler(s);
                }
            }
        });
    }

    private void loadRecycler(List<Rutina> s) {
        historial.addAll(s);
        historialAdapter = new RecyclerHistorialAdapter(historial);
        recycler.setAdapter(historialAdapter);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = historialAdapter.getPosition();

        if (item.getItemId() == R.id.titleInfo){
            sendRoutine(historialAdapter.getItem(position), R.id.action_historialFragment_to_infoHistorialFragment);
        }
        if (item.getItemId() == R.id.titleRemove){
            String titleDialog = "Eliminar";
            String messageDialog = "¿Borrar Historial " + historialAdapter.getItem(position).getNombre() + "?";
            Utils.showDialogDelete(requireContext(), titleDialog, messageDialog,
                    (dialogInterface, i) -> {
                        rutinaRepository.deleteHistorial(historialAdapter.getItem(position).getDate());
                        setData();
                    },null);
        }
        return false;
    }

    private void sendRoutine(Rutina rutina, int action) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(argParam1, rutina);
        Navigation.findNavController(requireView()).navigate(action, bundle);
    }
}