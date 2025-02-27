import {useEffect, useState} from "react";
import {apiShips} from "../pages/http";
import {toast} from "react-toastify";

export const useShipQuery = (id) => {
    const [shipStatus, setShip] = useState({isSuccess: false, isFailed: false, isLoading: true, data: null})

    const loadShip = () => {
        setShip({isSuccess: false, isFailed: false, isLoading: true, data: shipStatus.data})
        apiShips.get(`/${id}`).then(res => {
            setShip({isSuccess: true, isFailed: false, isLoading: false, data: res.data})
        }).catch(reason => {
            if (reason?.response?.data?.message) toast.error(reason.response.data.message)
            setShip({isSuccess: false, isFailed: true, isLoading: false, data: null})
        })
    }

    useEffect(() => {
        if (shipStatus.data == null) loadShip(id);
    }, []);

    const reloadShip = () => {
        loadShip(id)
    }

    return {shipStatus, reloadShip}
}