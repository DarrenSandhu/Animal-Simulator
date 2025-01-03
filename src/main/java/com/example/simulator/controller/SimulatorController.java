// package com.example.simulator.controller;

// import com.example.simulator.service.SimulatorService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;

// import java.util.HashMap;
// import java.util.Map;

// @RestController
// @RequestMapping("/api")
// public class SimulatorController {

//     @Autowired
//     private SimulatorService simulatorService;

//     @PostMapping("/start")
//     public Map<String, Object> startSimulation() {
//         simulatorService.simulateOneStepAsync();
//         return getSimulationStatus();
//     }

//     @GetMapping("/status")
//     public Map<String, Object> getSimulationStatus() {
//         Map<String, Object> data = new HashMap<>();
//         var simulator = simulatorService.getSimulator();
        
//         data.put("step", simulator.getStep());
//         data.put("weather", simulator.returnWeather());
//         data.put("timeOfDay", simulator.getDay().getTimeOfDay());
//         data.put("diseaseCount", simulator.getNumberofInfected());
        
//         return data;
//     }

//     @PostMapping("/reset")
//     public Map<String, Object> resetSimulation() {
//         simulatorService.resetSimulation();
//         return getSimulationStatus();
//     }

//     @GetMapping("/hello")
//     public String hello(){
//         return "Hello";
//     }
// }
