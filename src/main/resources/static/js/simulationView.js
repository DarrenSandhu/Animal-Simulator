// const gridContainer = document.getElementById('grid');

// // Function to fetch field data from the backend
// async function fetchFieldState() {
//     try {
//         const response = await fetch('/field');
//         const data = await response.json();

//         // Fetch the field object to determine the grid width (number of columns)
//         const field = await fetch('/fieldDimensions');
//         const fieldData = await field.json();
//         console.log('fieldData:', fieldData);
        
//         // Extract the width (number of columns) from the fieldData object
//         const fieldWidth = fieldData[1]; // Assuming 'width' is part of the fieldData
//         console.log('fieldWidth:', fieldWidth);

//         // Update the grid-template-columns dynamically based on the field width
//         const gridContainer = document.getElementById('grid');
//         gridContainer.style.gridTemplateColumns = `repeat(${fieldWidth}, 6px)`;  // Adjust cell width as needed


//         updateGrid(data);
//     } catch (error) {
//         console.error('Error fetching field data:', error);
//     }
// }

// // Function to update the grid
// function updateGrid(field) {
//     gridContainer.innerHTML = ''; // Clear existing grid

//     field.forEach(row => {
//         row.forEach(cell => {
//             const cellElement = document.createElement('div');
//             cellElement.className = 'cell ' + getClassForCell(cell);
//             gridContainer.appendChild(cellElement);
//         });
//     });
// }

// // Helper function to map cell types to CSS classes
function getClassForCell(cell) {
    switch (cell) {
        case 'Leopard':
            return 'leopard';
        case 'Deer':
            return 'deer';
        case 'Boar':
            return 'boar';
        case 'Tiger':
            return 'tiger';
        case 'Cheetah':
            return 'cheetah';   
        case 'Plant':
            return 'plant';
        default:
            return 'empty';
    }
}

// Fetch field state
function fetchFieldState() {
    fetch(`/${sessionId}/field`)
        .then(response => response.json())
        .then(data => {
            renderField(data);
        })
        .catch(error => console.error('Error fetching field state:', error));
}

// Render field on the UI
function renderField(field) {
    const fieldContainer = document.getElementById('fieldContainer');
    fieldContainer.innerHTML = ""; // Clear previous field

    field.forEach(row => {
        const rowDiv = document.createElement('div');
        rowDiv.classList.add('field-row');
        // Set id for each row
        rowDiv.id = 'fieldRow';

        row.forEach(cell => {
            const cellDiv = document.createElement('div');
            cellDiv.classList.add('field-cell');
            cellDiv.id = 'fieldCell';
            cellDiv.classList.add(getClassForCell(cell));
            rowDiv.appendChild(cellDiv);
        });

        fieldContainer.appendChild(rowDiv);
    });
}

