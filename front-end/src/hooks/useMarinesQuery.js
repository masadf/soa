import {useEffect, useState} from "react";
import {api} from "../pages/http";
import {toast} from "react-toastify";

export const useMarinesQuery = (startFiltration, startSortBy) => {
    const [marines, setMarines] = useState({isSuccess: false, isFailed: false, isLoading: true, data: null})
    const [sortBy, setSortBy] = useState(startSortBy ?? "")
    const [filterBy, setFilterBy] = useState(startFiltration ?? "")

    const loadMarines = (page, newFilterBy, newSortBy) => {
        setMarines({isSuccess: false, isFailed: false, isLoading: true, data: marines.data})
        let resultFilter = newFilterBy ? newFilterBy.map(el => "&filterBy=" + el).join("") : filterBy
        let resultSorter = newSortBy ? newSortBy.map(el => "&sortBy=" + el).join("") : sortBy
        setFilterBy(resultFilter)
        setSortBy(resultSorter)
        let path = `/marines?page=${page}`
        if (resultFilter) path = path + resultFilter
        if (resultSorter) path = path + resultSorter
        api.get(path).then(res => {
            setMarines({isSuccess: true, isFailed: false, isLoading: false, data: res.data})
        }).catch(reason => {
            if (reason?.response?.data?.message) toast.error(reason.response.data.message)
            setMarines({isSuccess: false, isFailed: true, isLoading: false, data: null})
        })
    }

    useEffect(() => {
        if (marines.data == null) loadMarines(1);
    }, []);

    const reloadMarines = (newFilterBy, newSortBy) => {
        loadMarines(1, newFilterBy, newSortBy)
    }

    return {marines, loadMarines, reloadMarines}
}