import {useState} from "react";
import {api} from "../pages/http";
import {toast} from "react-toastify";

export const useMarineQuery = () => {
    const [marineStatus, setMarine] = useState({isSuccess: false, isFailed: false, isLoading: false, data: null})

    const loadMarine = (id) => {
        setMarine({isSuccess: false, isFailed: false, isLoading: true, data: marineStatus.data})
        api.get(`/marines/${id}`).then(res => {
            setMarine({isSuccess: true, isFailed: false, isLoading: false, data: res.data})
        }).catch(reason => {
            if (reason?.response?.data?.message) toast.error(reason.response.data.message)
            setMarine({isSuccess: false, isFailed: true, isLoading: false, data: null})
        })
    }

    const reset = () => {
        setMarine({isSuccess: false, isFailed: false, isLoading: false, data: null})
    }

    return {marineStatus, loadMarine, reset}
}