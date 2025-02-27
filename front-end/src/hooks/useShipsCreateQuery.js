import {useState} from "react";
import {apiShips} from "../pages/http";
import {toast} from "react-toastify";

export const useShipCreateQuery = () => {
    const [creationStatus, setCreationStatus] = useState({isSuccess: false, isFailed: false, isLoading: false})

    const createShip = (request) => {
        setCreationStatus({isSuccess: false, isFailed: false, isLoading: true})
        apiShips.post(`/`, request).then(res => {
            setCreationStatus({isSuccess: true, isFailed: false, isLoading: false})
        }).catch(reason => {
            if (reason?.response?.data?.message) toast.error(reason.response.data.message)
            setCreationStatus({isSuccess: false, isFailed: true, isLoading: false})
        })
    }

    return {creationStatus, createShip}
}