import {useState} from "react";
import {apiShips} from "../pages/http";
import {toast} from "react-toastify";

export const useShipsDeleteQuery = () => {
    const [deletingStatus, setDeletingStatus] = useState({isSuccess: false, isFailed: false, isLoading: false})

    const deleteShip = (id) => {
        setDeletingStatus({isSuccess: false, isFailed: false, isLoading: true})
        apiShips.delete(`/${id}`).then(res => {
            setDeletingStatus({isSuccess: true, isFailed: false, isLoading: false})
        }).catch(reason => {
            if (reason?.response?.data?.message) toast.error(reason.response.data.message)
            setDeletingStatus({isSuccess: false, isFailed: true, isLoading: false})
        })
    }

    return {deletingStatus, deleteShip}
}