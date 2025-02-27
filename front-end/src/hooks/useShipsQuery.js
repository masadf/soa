import {useEffect, useState} from "react";
import {apiShips} from "../pages/http";
import {toast} from "react-toastify";

export const useShipsQuery = () => {
    const [ships, setShips] = useState({isSuccess: false, isFailed: false, isLoading: true, data: null})

    const loadShips = () => {
        setShips({isSuccess: false, isFailed: false, isLoading: true, data: ships.data})
        apiShips.get(`/`).then(res => {
            setShips({isSuccess: true, isFailed: false, isLoading: false, data: res.data})
        }).catch(reason => {
            if (reason?.response?.data?.message) toast.error(reason.response.data.message)
            setShips({isSuccess: false, isFailed: true, isLoading: false, data: null})
        })
    }

    useEffect(() => {
        if (ships.data == null) loadShips();
    }, []);

    const reloadShips = () => {
        loadShips()
    }

    return {ships, reloadShips}
}