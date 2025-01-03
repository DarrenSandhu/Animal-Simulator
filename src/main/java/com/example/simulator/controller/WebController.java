// package com.example.simulator.controller;
// import com.example.simulator.Field;
// import com.example.simulator.Location;
// import com.example.simulator.service.SimulatorService;


// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;

// import java.util.List;
// import java.util.stream.Collectors;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.ResponseBody;


// @Controller
// public class WebController {

//     private static final Logger logger = LoggerFactory.getLogger(WebController.class);

//     @Autowired
//     private SimulatorService simulatorService;

//     @GetMapping("/home")
//     public String home() {
//         try {
//             return "home";
//         } catch (Exception e) {
//             logger.error("Error occurred while going home", e);
//             return "error"; // You can render an error page here if needed.
//         }
//     }

//     @PostMapping("/start")
//     public String startSimulation() {
//         simulatorService.simulateOneStepAsync();
//         // model.addAttribute("locations", simulatorService.getAnimalLocations());
//         return "home";
//     }

//     @PostMapping("/run")
//     public String runLongSimulation() {
//         simulatorService.runLongSimulationAsync();
//         return "home";
//     }

//     @GetMapping("/status")
//     public @ResponseBody String getSimulationStatus() {
//         try {
//             // Return the simulation status directly as a plain text response
//             return simulatorService.getSimulationStatus();
//         } catch (Exception e) {
//             logger.error("Error occurred while getting simulation status", e);
//             return "Error fetching status"; // Fallback message for errors
//         }
//     }

//     @PostMapping("/reset")
//     public String resetSimulation() {
//         simulatorService.resetSimulation();
//         return "home";
//     }

//     // Pause current simulation
//     @PostMapping("/pause")
//     public String pauseSimulation() {
//         simulatorService.pauseSimulation();
//         return "home";
//     }

//     // Resume current simulation
//     @PostMapping("/resume")
//     public String resumeSimulation() {
//         simulatorService.resumeSimulation();
//         return "home";
//     }

//     // Get the current field status
//     @GetMapping("/field")
//     public @ResponseBody List<List<String>> getFieldState() {
//         Field field = simulatorService.getField();
//         int depth = field.getDepth();
//         int width = field.getWidth();
        
//         // Convert the field into a 2D array of strings for JSON
//         return java.util.stream.IntStream.range(0, depth)
//             .mapToObj(row -> java.util.stream.IntStream.range(0, width)
//                 .mapToObj(col -> {
//                     Location location = new Location(row, col);
//                     if(field.isEmpty(location)) {
//                         return "Empty";
//                     }
//                     else{
//                         return field.getObjectAt(row, col).getClass().getSimpleName();
//                     }
//                 })
//                 .collect(Collectors.toList()))
//             .collect(Collectors.toList());
//     }

//     // Get Field
//     @GetMapping("/fieldDimensions")
//     public @ResponseBody int[] getFieldDimesions() {
//         return simulatorService.getFieldDimensions();
//     }


// }

package com.example.simulator.controller;

import com.example.simulator.Field;
import com.example.simulator.Location;
import com.example.simulator.service.SimulatorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WebController {

    private static final Logger logger = LoggerFactory.getLogger(WebController.class);

    @Autowired
    private SimulatorService simulatorService;

    // Render home page
    @GetMapping("/")
    public String home() {
        try {
            return "home";
        } catch (Exception e) {
            logger.error("Error occurred while loading the home page", e);
            return "error";
        }
    }

    // Start a single simulation step
    @PostMapping("/{sessionId}/start")
    public ResponseEntity<String> startSimulation(@PathVariable String sessionId) {
        try {
            simulatorService.simulateOneStepAsync(sessionId);
            return ResponseEntity.ok("Simulation STEP started successfully.");
        } catch (Exception e) {
            logger.error("Error occurred while starting the simulation", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error starting simulation");
        }
    }

    // Run a long simulation
    @PostMapping("/{sessionId}/run")
    public String runLongSimulation(@PathVariable String sessionId) {
        try {
            simulatorService.runLongSimulationAsync(sessionId);
            return "home";
        } catch (Exception e) {
            logger.error("Error occurred while running the simulation", e);
            return "error";
        }
    }

    // Get simulation status
    @GetMapping("/{sessionId}/status")
    public @ResponseBody String getSimulationStatus(@PathVariable String sessionId) {
        try {
            return simulatorService.getSimulationStatus(sessionId);
        } catch (Exception e) {
            logger.error("Error occurred while fetching simulation status", e);
            return "Error fetching simulation status";
        }
    }

    // Reset the simulation
    @PostMapping("/{sessionId}/reset")
    public String resetSimulation(@PathVariable String sessionId) {
        try {
            simulatorService.resetSimulation(sessionId);
            return "home";
        } catch (Exception e) {
            logger.error("Error occurred while resetting the simulation", e);
            return "error";
        }
    }

    // Pause the simulation
    @PostMapping("/{sessionId}/pause")
    public String pauseSimulation(@PathVariable String sessionId) {
        try {
            simulatorService.pauseSimulation(sessionId);
            return "home";
        } catch (Exception e) {
            logger.error("Error occurred while pausing the simulation", e);
            return "error";
        }
    }

    // Resume the simulation
    @PostMapping("/{sessionId}/resume")
    public ResponseEntity<String> resumeSimulation(@PathVariable String sessionId) {
        try {
            simulatorService.resumeSimulation(sessionId);
            return ResponseEntity.ok("Simulation resumed successfully.");
        } catch (Exception e) {
            logger.error("Error occurred while resuming the simulation", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error resuming simulation");
        }
    }

    // Get the field state
    @GetMapping("/{sessionId}/field")
    public @ResponseBody List<List<String>> getFieldState(@PathVariable String sessionId) {
        try {
            Field field = simulatorService.getField(sessionId);
            int depth = field.getDepth();
            int width = field.getWidth();

            // Convert the field into a 2D array of strings for JSON response
            return java.util.stream.IntStream.range(0, depth)
                .mapToObj(row -> java.util.stream.IntStream.range(0, width)
                    .mapToObj(col -> {
                        Location location = new Location(row, col);
                        if (field.isEmpty(location)) {
                            return "Empty";
                        } else {
                            return field.getObjectAt(row, col).getClass().getSimpleName();
                        }
                    })
                    .collect(Collectors.toList()))
                .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error occurred while fetching the field state", e);
            throw new RuntimeException("Error fetching field state");
        }
    }

    // Get field dimensions
    @GetMapping("/{sessionId}/fieldDimensions")
    public @ResponseBody int[] getFieldDimensions(@PathVariable String sessionId) {
        try {
            return simulatorService.getFieldDimensions(sessionId);
        } catch (Exception e) {
            logger.error("Error occurred while fetching field dimensions", e);
            throw new RuntimeException("Error fetching field dimensions");
        }
    }

}


