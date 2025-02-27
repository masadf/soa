import {useEffect, useState} from "react";
import {api} from "../pages/http";
import {toast} from "react-toastify";

export const useGroupingByNameQuery = () => {
    const [groupByNameStatus, setGroupByNameStatus] = useState({isSuccess: false, isFailed: false, isLoading: true, data: null})

    const groupByName = () => {
        setGroupByNameStatus({isSuccess: false, isFailed: false, isLoading: true, data: null})
        api.get(`/marines/name/grouping/size`).then(res => {
            setGroupByNameStatus({isSuccess: true, isFailed: false, isLoading: false, data: res.data})
        }).catch(reason => {
            if (reason?.response?.data?.message) toast.error(reason.response.data.message)
            setGroupByNameStatus({isSuccess: false, isFailed: true, isLoading: false, data: null})
        })
    }

    useEffect(() => {
        if (groupByNameStatus.data == null) groupByName();
    }, []);

    return {groupByNameStatus}
}