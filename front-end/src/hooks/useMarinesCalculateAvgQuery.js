import {useEffect, useState} from "react";
import {api} from "../pages/http";
import {toast} from "react-toastify";

export const useMarinesCalculateAvgQuery = () => {
    const [avgStatus, setAvgData] = useState({isSuccess: false, isFailed: false, isLoading: true, data: null})

    const loadAvg = () => {
        setAvgData({isSuccess: false, isFailed: false, isLoading: true, data: avgStatus.data})
        api.post(`/marines/health/avg`).then(res => {
            setAvgData({isSuccess: true, isFailed: false, isLoading: false, data: res.data})
        }).catch(reason => {
            if (reason?.response?.data?.message) toast.error(reason.response.data.message)
            setAvgData({isSuccess: false, isFailed: true, isLoading: false, data: null})
        })
    }

    useEffect(() => {
        if (avgStatus.data == null) loadAvg();
    }, []);

    const reloadAvg = () => {
        loadAvg()
    }

    return {avgStatus, reloadAvg}
}