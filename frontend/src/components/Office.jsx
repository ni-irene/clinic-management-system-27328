
import axios from 'axios';
import { useEffect, useState } from 'react';

function Office() {
    const [offices, setOffices] = useState([]);
    const [search, setSearch] = useState("");

    async function getOffices() {
        try {
            const response = await axios.get(
                'http://localhost:8082/api/offices'
            );
            setOffices(response.data);
        } catch (error) {
            alert("Could not load offices: " + error.message);
        }
    }

    useEffect(() => {
        getOffices();
    }, []);

    const filteredOffices = offices.filter(office =>
        String(office.roomNumber ?? '').toLowerCase().includes(search.toLowerCase()) ||
        String(office.floor ?? '').toLowerCase().includes(search.toLowerCase())
    );

    async function deleteOffice(id) {
        const confirmDelete = window.confirm(
            "Are you sure you want to delete this office?"
        );

        if (!confirmDelete) return;

        try {
            await axios.delete(
                'http://localhost:8082/api/offices/' + id
            );

            alert("Office deleted successfully");
            getOffices();
        } catch (error) {
            alert("Office not deleted: " + error.message);
        }
    }

    return (
        <div>
            <h2>AVAILABLE OFFICES</h2>

            <input
                type="text"
                placeholder="Search by room number or floor"
                value={search}
                onChange={e => setSearch(e.target.value)}
            />

            <table border="1">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Room Number</th>
                        <th>Floor</th>
                        <th>Action</th>
                    </tr>
                </thead>

                <tbody>
                    {filteredOffices.length > 0 ? (
                        filteredOffices.map(office => (
                            <tr key={office.id}>
                                <td>{office.id}</td>
                                <td>{office.roomNumber}</td>
                                <td>{office.floor}</td>
                                <td>
                                    <button onClick={() => deleteOffice(office.id)}>
                                        Delete
                                    </button>
                                    <button disabled>
                                        Update
                                    </button>
                                </td>
                            </tr>
                        ))
                    ) : (
                        <tr>
                            <td colSpan="4">No offices found</td>
                        </tr>
                    )}
                </tbody>
            </table>
        </div>
    );
}

export default Office;
