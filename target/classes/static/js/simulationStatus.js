// let pollingInterval = null;
// let simulationViewPollingInterval = null;

// function fetchSimulationStatus() {
//     fetch('/status')
//         .then(response => response.text())
//         .then(data => {

//             document.getElementById('simulationStatus').innerText = data;
//         })
//         .catch(error => console.error('Error fetching status:', error));
// }

// function startPolling() {
//     if (!pollingInterval) {
//         simulationViewPollingInterval = setInterval(fetchFieldState, 100);
//         pollingInterval = setInterval(fetchSimulationStatus, 100);
        
//     }
// }

// function stopPolling() {
//     if (pollingInterval) {
//         clearInterval(pollingInterval);
//         clearInterval(simulationViewPollingInterval);
//         pollingInterval = null;
//         simulationViewPollingInterval = null;
//         fetchSimulationStatus(); // Fetch one last time to update the status immediately
//         fetchFieldState(); // Fetch one last time to update the grid immediately
//     }
// }

// function setButtonState({ run, pause, resume }) {
//     document.getElementById('runButton').disabled = !run;
//     document.getElementById('pauseButton').disabled = !pause;
//     document.getElementById('resumeButton').disabled = !resume;
// }

// // Start Simulation
// function startSimulation() {
//     fetch('/run', { method: 'POST' })
//         .then(() => {
//             startPolling();
//             setButtonState({ run: false, pause: true, resume: false });
//         })
//         .catch(error => console.error('Error starting simulation:', error));
// }

// // Pause Simulation
// function pauseSimulation() {
//     fetch('/pause', { method: 'POST' })
//         .then(() => {
//             stopPolling();
//             setButtonState({ run: false, pause: false, resume: true });
//         })
//         .catch(error => console.error('Error pausing simulation:', error));
// }

// // Resume Simulation
// function resumeSimulation() {
//     fetch('/resume', { method: 'POST' })
//         .then(() => {
//             startPolling();
//             setButtonState({ run: false, pause: true, resume: false });
//         })
//         .catch(error => console.error('Error resuming simulation:', error));
// }

// // Simulate One Step
// function simulateOneStep() {
//     fetch('/start', { method: 'POST' })
//         .then(() => {
//             fetchSimulationStatus();
//             fetchFieldState();
//         })
//         .catch(error => console.error('Error simulating one step:', error));
// }

// // Reset Simulation
// function resetSimulation() {
//     stopPolling();
//     fetch('/reset', { method: 'POST' })
//         .then(() => {
//             fetchFieldState();
//             fetchSimulationStatus();
//             setButtonState({ run: true, pause: false, resume: false });
//         })
//         .catch(error => console.error('Error resetting simulation:', error));
// }

// // Initialize state when the page loads
// document.addEventListener("DOMContentLoaded", () => {
//     fetchSimulationStatus();
//     fetchFieldState();
//     setButtonState({ run: true, pause: false, resume: false });
// });

let pollingInterval = null;
let simulationViewPollingInterval = null;
const protocol = window.location.protocol;
const hostname = window.location.hostname;
const url = `${protocol}//${hostname}`;

// Replace with actual session ID logic (e.g., fetch from server or localStorage)
let sessionId = localStorage.getItem('sessionId')

if (!sessionId) {
    sessionId = generateSessionId();
    localStorage.setItem('sessionId', sessionId);
}
console.log('Session ID:', sessionId);

function generateSessionId() {
    return 'xxxx-xxxx-4xxx-yxxx-xxxx'.replace(/[xy]/g, function(c) {
        const r = Math.random() * 16 | 0, v = c === 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}

// Fetch simulation status
function fetchSimulationStatus() {
    fetch(`/${sessionId}/status`)
        .then(response => response.text())
        .then(data => {
            document.getElementById('simulationStatus').innerText = data;
        })
        .catch(error => console.error('Error fetching status:', error));
}

// Start polling for updates
function startPolling() {
    if (!pollingInterval) {
        simulationViewPollingInterval = setInterval(fetchFieldState, 100); // Adjust as needed
        pollingInterval = setInterval(fetchSimulationStatus, 100);
    }
}

// Stop polling for updates
function stopPolling() {
    if (pollingInterval) {
        clearInterval(pollingInterval);
        clearInterval(simulationViewPollingInterval);
        pollingInterval = null;
        simulationViewPollingInterval = null;

        // Fetch one last time to ensure UI is up-to-date
        fetchSimulationStatus();
        fetchFieldState();
    }
}

// Update button states
function setButtonState(states) {
    document.getElementById('runButton').disabled = !states.run;
    document.getElementById('pauseButton').disabled = !states.pause;
    document.getElementById('resumeButton').disabled = !states.resume;
}

// Start simulation
function startSimulation() {
    fetch(`/${sessionId}/run`, { method: 'POST' })
        .then(() => {
            startPolling();
            setButtonState({ run: false, pause: true, resume: false});
        })
        .catch(error => console.error('Error starting simulation:', error));
}

// Pause simulation
function pauseSimulation() {
    fetch(`/${sessionId}/pause`, { method: 'POST' })
        .then(() => {
            stopPolling();
            setButtonState({ run: false, pause: false, resume: true});
        })
        .catch(error => console.error('Error pausing simulation:', error));
}

// Resume simulation
function resumeSimulation() {
    fetch(`/${sessionId}/resume`, { method: 'POST' })
        .then(() => {
            startPolling();
            fetchSimulationStatus();
            setButtonState({ run: false, pause: true, resume: false});
        })
        .catch(error => console.error('Error resuming simulation:', error));
}

// Simulate one step
function simulateOneStep() {
    fetch(`/${sessionId}/start`, { method: 'POST' })
        .then(() => {
            fetchSimulationStatus();
            fetchFieldState();
        })
        .catch(error => console.error('Error simulating one step:', error));
}

// Reset simulation
function resetSimulation() {
    stopPolling();
    fetch(`/${sessionId}/reset`, { method: 'POST' })
        .then(() => {
            fetchFieldState();
            fetchSimulationStatus();
            setButtonState({ run: true, pause: false, resume: false});
        })
        .catch(error => console.error('Error resetting simulation:', error));
}

// Initialize UI when the page loads
document.addEventListener("DOMContentLoaded", () => {
    fetchSimulationStatus();
    fetchFieldState();
    setButtonState({ run: true, pause: false, resume: false});
});
