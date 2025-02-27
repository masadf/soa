import {useState} from "react";
import {api} from "../pages/http";
import {toast} from "react-toastify";

export const useMarinesUpdateQuery = () => {
    const [updatingStatus, setUpdatingStatus] = useState({isSuccess: false, isFailed: false, isLoading: false})

    const updateMarine = (id, updateRequest) => {
        setUpdatingStatus({isSuccess: false, isFailed: false, isLoading: true})
        api.put(`/marines/${id}`, updateRequest).then(res => {
            setUpdatingStatus({isSuccess: true, isFailed: false, isLoading: false})
        }).catch(reason => {
            if (reason?.response?.data?.message) toast.error(reason.response.data.message)
            setUpdatingStatus({isSuccess: false, isFailed: true, isLoading: false})
        })
    }

    return {updatingStatus, updateMarine}
}