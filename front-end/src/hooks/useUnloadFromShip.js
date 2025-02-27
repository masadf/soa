import {useState} from "react";
import {apiShips} from "../pages/http";
import {toast} from "react-toastify";

export const useShipUnloadQuery = (id) => {
    const [unloadStatus, setUnloadStatus] = useState({isSuccess: false, isFailed: false, isLoading: false})

    const unloadShip = () => {
        setUnloadStatus({isSuccess: false, isFailed: false, isLoading: true})
        apiShips.patch(`/${id}/unload-all`).then(res => {
            setUnloadStatus({isSuccess: true, isFailed: false, isLoading: false})
        }).catch(reason => {
            if (reason?.response?.data?.message) toast.error(reason.response.data.message)
            setUnloadStatus({isSuccess: false, isFailed: true, isLoading: false})
        })
    }

    const resetUnloadShips = () => {
        setUnloadStatus({isSuccess: false, isFailed: false, isLoading: false})
    }

    return {unloadStatus, unloadShip, resetUnloadShips}
}