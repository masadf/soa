import {useState} from "react";
import {api} from "../pages/http";
import {toast} from "react-toastify";

export const useMarinesDeleteQuery = () => {
    const [deletingStatus, setDeletingStatus] = useState({isSuccess: false, isFailed: false, isLoading: false})

    const deleteMarine = (id) => {
        setDeletingStatus({isSuccess: false, isFailed: false, isLoading: true})
        api.delete(`/marines/${id}`).then(res => {
            setDeletingStatus({isSuccess: true, isFailed: false, isLoading: false})
        }).catch(reason => {
            if (reason?.response?.data?.message) toast.error(reason.response.data.message)
            setDeletingStatus({isSuccess: false, isFailed: true, isLoading: false})
        })
    }

    return {deletingStatus, deleteMarine}
}