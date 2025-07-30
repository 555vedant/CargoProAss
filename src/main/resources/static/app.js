async function fetchLoads() {
  try {
    const response = await fetch("/api/loads?page=0&size=10");
    const data = await response.json();
    const loads = data.content;
    const loadsBody = document.getElementById("loads-body");
    loadsBody.innerHTML = "";
    loads.forEach((load) => {
      const row = document.createElement("tr");
      row.innerHTML = `
                <td>${load.id}</td>
                <td>${load.shipperId}</td>
                <td>${load.facility.loadingPoint}</td>
                <td>${load.facility.unloadingPoint}</td>
                <td>${load.productType}</td>
                <td>${load.truckType}</td>
                <td>${load.noOfTrucks}</td>
                <td>${load.weight}</td>
                <td>${load.comment || ""}</td>
                <td>${load.status}</td>
                <td>${new Date(load.datePosted).toLocaleString()}</td>
                

            `;
      loadsBody.appendChild(row);
    });
  } catch (error) {
    console.error("Error fetching loads:", error);
    alert("Failed to fetch loads. Please try again.");
  }
}

async function fetchBookings() {
  try {
    const response = await fetch("/api/bookings?page=0&size=10");
    const data = await response.json();
    const bookings = data.content;
    const bookingsBody = document.getElementById("bookings-body");
    bookingsBody.innerHTML = "";
    bookings.forEach((booking) => {
      const row = document.createElement("tr");
      row.innerHTML = `
                <td>${booking.id}</td>
                <td>${booking.loadId}</td>
                <td>${booking.transporterId}</td>
                <td>${booking.proposedRate}</td>
                <td>${booking.comment || ""}</td>
                <td>${booking.status}</td>
                <td>${new Date(booking.requestedAt).toLocaleString()}</td>
                
            `;
      bookingsBody.appendChild(row);
    });
  } catch (error) {
    console.error("Error fetching bookings:", error);
    alert("Failed to fetch bookings. Please try again.");
  }
}

function showLoadForm() {
  document.getElementById("create-load-form").style.display = "block";
}

function hideLoadForm() {
  document.getElementById("create-load-form").style.display = "none";
  document.getElementById("create-load-form").reset();
}

function showBookingForm() {
  document.getElementById("create-booking-form").style.display = "block";
}

function hideBookingForm() {
  document.getElementById("create-booking-form").style.display = "none";
  document.getElementById("create-booking-form").reset();
}

async function createLoad(event) {
  event.preventDefault();
  try {
    const load = {
      shipperId: document.getElementById("shipperId").value,
      facility: {
        loadingPoint: document.getElementById("loadingPoint").value,
        unloadingPoint: document.getElementById("unloadingPoint").value,
        loadingDate: new Date(
          document.getElementById("loadingDate").value
        ).toISOString(),
        unloadingDate: new Date(
          document.getElementById("unloadingDate").value
        ).toISOString(),
      },
      productType: document.getElementById("productType").value,
      truckType: document.getElementById("truckType").value,
      noOfTrucks: parseInt(document.getElementById("noOfTrucks").value),
      weight: parseFloat(document.getElementById("weight").value),
      comment: document.getElementById("comment").value,
      datePosted: new Date().toISOString(),
      status: "POSTED",
    };
    const response = await fetch("/api/loads", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(load),
    });
    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }
    await fetchLoads();
    hideLoadForm();
  } catch (error) {
    console.error("Error creating load:", error);
    alert("Failed to create load. Please check your input and try again.");
  }
}

async function createBooking(event) {
  event.preventDefault();
  try {
    const booking = {
      loadId: document.getElementById("loadId").value,
      transporterId: document.getElementById("transporterId").value,
      proposedRate: parseFloat(document.getElementById("proposedRate").value),
      comment: document.getElementById("commentBooking").value,
      status: "PENDING",
      requestedAt: new Date().toISOString(),
    };
    const response = await fetch("/api/bookings", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(booking),
    });
    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }
    await fetchBookings();
    hideBookingForm();
  } catch (error) {
    console.error("Error creating booking:", error);
    alert(
      "Failed to create booking. Please ensure the Load ID is valid and try again."
    );
  }
}

// async function deleteLoad(id) {
//   if (!confirm("Are you sure you want to delete this load?")) return;
//   try {
//     const response = await fetch(`/api/loads/${id}`, {
//       method: "DELETE",
//     });
//     if (!response.ok) {
//       throw new Error(`HTTP error! Status: ${response.status}`);
//     }
//     await fetchLoads();
//   } catch (error) {
//     console.error("Error deleting load:", error);
//     alert("Failed to delete load. Please try again.");
//   }
// }

// async function deleteBooking(id) {
//   if (!confirm("Are you sure you want to delete this booking?")) return;
//   try {
//     const response = await fetch(`/api/bookings/${id}`, {
//       method: "DELETE",
//     });
//     if (!response.ok) {
//       throw new Error(`HTTP error! Status: ${response.status}`);
//     }
//     await fetchBookings();
//   } catch (error) {
//     console.error("Error deleting booking:", error);
//     alert("Failed to delete booking. Please try again.");
//   }
// }

document.addEventListener("DOMContentLoaded", () => {
  fetchLoads();
  fetchBookings();
});
