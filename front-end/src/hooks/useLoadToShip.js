import {useState} from "react";
import {apiShips} from "../pages/http";
import {toast} from "react-toastify";

export const useLoadToShip = (id) => {
    const [loadStatus, setLoadStatus] = useState({isSuccess: false, isFailed: false, isLoading: false})

    const loadToShip = (marineId) => {
        setLoadStatus({isSuccess: false, isFailed: false, isLoading: true})
        apiShips.patch(`/${id}/load/${marineId}`).then(res => {
            setLoadStatus({isSuccess: true, isFailed: false, isLoading: false})
        }).catch(reason => {
            if (reason?.response?.data?.message) toast.error(reason.response.data.message)
            setLoadStatus({isSuccess: false, isFailed: true, isLoading: false})
        })
    }

    return {loadStatus, loadToShip}
}