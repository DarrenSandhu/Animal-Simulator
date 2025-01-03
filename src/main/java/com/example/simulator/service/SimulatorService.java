package com.example.simulator.service;

import com.example.simulator.Simulator;
import com.example.simulator.Field;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
@Service
public class SimulatorService {

    private ConcurrentHashMap<String, Simulator> simulators;
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SimulatorService.class);

    public SimulatorService() {
        this.simulators = new ConcurrentHashMap<>();
    }

    // Get or create a simulator for a session
    public Simulator getOrCreateSimulator(String sessionId) {
        return simulators.computeIfAbsent(sessionId, id -> new Simulator());
    }

    // Remove a simulator for cleanup
    public void removeSimulator(String sessionId) {
        simulators.remove(sessionId);
    }

    // Get the simulator field for a session
    public Field getField(String sessionId) {
        return getOrCreateSimulator(sessionId).getField();
    }

    // Get field dimensions for a session
    public int[] getFieldDimensions(String sessionId) {
        Simulator simulator = getOrCreateSimulator(sessionId);
        return new int[]{simulator.getField().getDepth(), simulator.getField().getWidth()};
    }

    // Get the current simulation status for a session
    public String getSimulationStatus(String sessionId) {
        return getOrCreateSimulator(sessionId).getSimulationStatus();
    }

    // Simulate one step asynchronously for a session
    @Async
    public CompletableFuture<Void> simulateOneStepAsync(String sessionId) {
        try {
            getOrCreateSimulator(sessionId).simulateOneStep();
        } catch (Exception e) {
            logger.error("Error occurred during simulation step for session: " + sessionId, e);
        }
        return CompletableFuture.completedFuture(null);
    }

    // Run the simulation for a long period asynchronously
    @Async
    public CompletableFuture<Void> runLongSimulationAsync(String sessionId) {
        try {
            getOrCreateSimulator(sessionId).runLongSimulation();
        } catch (Exception e) {
            logger.error("Error occurred during long simulation for session: " + sessionId, e);
        }
        return CompletableFuture.completedFuture(null);
    }

    // Reset the simulation asynchronously
    @Async
    public CompletableFuture<Void> resetSimulation(String sessionId) {
        try {
            getOrCreateSimulator(sessionId).reset();
        } catch (Exception e) {
            logger.error("Error occurred during simulation reset for session: " + sessionId, e);
        }
        return CompletableFuture.completedFuture(null);
    }

    // Pause the simulation asynchronously
    @Async
    public CompletableFuture<Void> pauseSimulation(String sessionId) {
        try {
            getOrCreateSimulator(sessionId).pauseSimulation();
        } catch (Exception e) {
            logger.error("Error occurred during simulation pause for session: " + sessionId, e);
        }
        return CompletableFuture.completedFuture(null);
    }

    // Resume the simulation asynchronously
    public void resumeSimulation(String sessionId) {
        try {
            getOrCreateSimulator(sessionId).resumeSimulation();
        } catch (Exception e) {
            logger.error("Error occurred during simulation resume for session: " + sessionId, e);
        }
    }
}
// @Service
// public class SimulatorService {

//     // Store simulators by a unique session or request ID
//     private ConcurrentHashMap<String, Simulator> simulators;

//     // Initialize the simulator service with an empty map
//     public SimulatorService() {
//         this.simulators = new ConcurrentHashMap<>();
//     }

//     // Get or create a simulator for a given session ID
//     public Simulator getOrCreateSimulator(String sessionId) {
//         return simulators.computeIfAbsent(sessionId, id -> new Simulator());
//     }

//     // Remove a simulator for cleanup (optional)
//     public void removeSimulator(String sessionId) {
//         simulators.remove(sessionId);
//     }

//     // Get the simulator field for a session
//     public Field getField(String sessionId) {
//         return getOrCreateSimulator(sessionId).getField();
//     }

//     // Get the field dimensions for a session
//     public int[] getFieldDimensions(String sessionId) {
//         Simulator simulator = getOrCreateSimulator(sessionId);
//         return new int[]{simulator.getField().getDepth(), simulator.getField().getWidth()};
//     }

//     // Get the current simulation status for a session
//     public String getSimulationStatus(String sessionId) {
//         return getOrCreateSimulator(sessionId).getSimulationStatus();
//     }

//     // Simulate one step asynchronously for a session
//     @Async
//     public void simulateOneStepAsync(String sessionId) {
//         getOrCreateSimulator(sessionId).simulateOneStep();
//     }

//     // Run the simulation for a long period asynchronously for a session
//     @Async
//     public void runLongSimulationAsync(String sessionId) {
//         getOrCreateSimulator(sessionId).runLongSimulation();
//     }

//     // Reset the simulation for a session
//     @Async
//     public void resetSimulation(String sessionId) {
//         getOrCreateSimulator(sessionId).reset();
//     }

//     // Pause the simulation for a session
//     @Async
//     public void pauseSimulation(String sessionId) {
//         getOrCreateSimulator(sessionId).pauseSimulation();
//     }

//     // Resume the simulation for a session
//     @Async
//     public void resumeSimulation(String sessionId) {
//         getOrCreateSimulator(sessionId).resumeSimulation();
//     }
// }

// package com.example.simulator.service;

// import com.example.simulator.Simulator;
// import com.example.simulator.SimulatorView;
// import com.example.simulator.Field;

// import java.util.concurrent.ConcurrentHashMap;

// import org.springframework.scheduling.annotation.Async;
// import org.springframework.stereotype.Service;

// @Service
// public class SimulatorService {

//     // Store simulators by a unique session or request ID
//     private ConcurrentHashMap<String, Simulator> simulators = new ConcurrentHashMap<>();


//     // Initialize the simulator
//     public SimulatorService() {
//         this
//     }

//     // Get the simulator instance
//     public Simulator getSimulator() {
//         return simulator;
//     }

//     // Get the simulator field
//     public Field getField() {
//         return simulator.getField();
//     }

//     // Get the field dimensions
//     public int[] getFieldDimensions() {
//         return new int[]{simulator.getField().getDepth(), simulator.getField().getWidth()};
//     }

//     // Get the current simulation status
//     public String getSimulationStatus() {
//         return simulator.getSimulationStatus();
//     }

//     // Simulate one step asynchronously
//     @Async
//     public void simulateOneStepAsync() {
//         simulator.simulateOneStep();
//     }

//     // Run the simulation for a long period asynchronously
//     @Async
//     public void runLongSimulationAsync() {
//         simulator.runLongSimulation();
//     }

//     // Reset the simulation
//     @Async
//     public void resetSimulation() {
//         simulator.reset();
//     }

//     // Pause current simulation
//     @Async
//     public void pauseSimulation() {
//         simulator.pauseSimulation();
//     }

//     // Resume current simulation
//     @Async
//     public void resumeSimulation() {
//         simulator.resumeSimulation();
//     }
// }