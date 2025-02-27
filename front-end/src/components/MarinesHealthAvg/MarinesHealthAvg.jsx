import {CircularProgress} from "@mui/material";
import {useMarinesCalculateAvgQuery} from "../../hooks/useMarinesCalculateAvgQuery";

export const MarinesHealthAvg = () => {
    const {avgStatus, loadAvg, reloadAvg} = useMarinesCalculateAvgQuery()

    if (avgStatus.isLoading) return <CircularProgress/>

    return <div>{avgStatus.data.result}</div>
}