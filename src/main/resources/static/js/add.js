document.getElementById('add-movie-form').addEventListener('submit', function (e) {
  e.preventDefault(); // Prevent page reload

  const name = document.getElementById('movie-name').value.trim();
  const description = document.getElementById('movie-description').value.trim();

  if (!name || !description) {
    alert('Please fill out all fields!');
    return;
  }

  // Placeholder for poster upload functionality
  alert(`Movie "${name}" added successfully!\nDescription: ${description}`);
});
