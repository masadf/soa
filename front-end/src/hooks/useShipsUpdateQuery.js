import {useState} from "react";
import {apiShips} from "../pages/http";
import {toast} from "react-toastify";

export const useShipUpdateQuery = () => {
    const [updatingStatus, setUpdatingStatus] = useState({isSuccess: false, isFailed: false, isLoading: false})

    const updateShip = (id, updateRequest) => {
        setUpdatingStatus({isSuccess: false, isFailed: false, isLoading: true})
        apiShips.put(`/${id}`, updateRequest).then(res => {
            setUpdatingStatus({isSuccess: true, isFailed: false, isLoading: false})
        }).catch(reason => {
            if (reason?.response?.data?.message) toast.error(reason.response.data.message)
            setUpdatingStatus({isSuccess: false, isFailed: true, isLoading: false})
        })
    }

    return {updatingStatus, updateShip}
}