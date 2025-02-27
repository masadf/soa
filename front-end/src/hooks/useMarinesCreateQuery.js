import {useState} from "react";
import {api} from "../pages/http";
import {toast} from "react-toastify";

export const useMarinesCreateQuery = () => {
    const [creationStatus, setCreationStatus] = useState({isSuccess: false, isFailed: false, isLoading: false})

    const createMarine = (request) => {
        setCreationStatus({isSuccess: false, isFailed: false, isLoading: true})
        api.post(`/marines`, request).then(res => {
            setCreationStatus({isSuccess: true, isFailed: false, isLoading: false})
        }).catch(reason => {
            if (reason?.response?.data?.message) toast.error(reason.response.data.message)
            setCreationStatus({isSuccess: false, isFailed: true, isLoading: false})
        })
    }

    return {creationStatus, createMarine}
}