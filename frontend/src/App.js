import React, { useEffect, useState } from 'react';

function App() {
  const [events, setEvents] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8080/events')
        .then(response => response.json())
        .then(data => setEvents(data));
  }, []);

  return (
      <div>
        <h1>Events</h1>
        <ul>
          {events.map((event, index) => (
              <li key={index}>{event.name}</li>
          ))}
        </ul>
      </div>
  );
}

export default App;
