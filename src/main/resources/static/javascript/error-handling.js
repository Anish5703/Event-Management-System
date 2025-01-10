// JavaScript to hide the error message after 5 seconds
    function hideErrorMessage() {
      const errorDiv = document.getElementById('error-group'); // Correct variable name
      if (errorDiv) {
        setTimeout(() => {
			errorDiv.style.color = 'green';
          errorDiv.style.display = 'none';
        }, 5000); // Hide after 5000 milliseconds (5 seconds)
      }
    }