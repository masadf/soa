import {useEffect, useState} from "react";

export const getOperations = (field) => {
    switch (field) {
        case "name":
        case "category":
        case "weaponType":
        case "meleeWeapon":
        case "chapter.name":
            return ["="]
        default:
            return [">", "=", "<", ">=", "<="]
    }
}

export const useTypes = (field, callback) => {
    const [currentType, setCurrentType] = useState({type: "input"})
    const setFieldType = () => {
        switch (field) {
            case "category":
                setCurrentType({
                    type: "select",
                    values: [
                        "AGGRESSOR",
                        "SUPPRESSOR",
                        "TERMINATOR",
                        "HELIX",
                        "APOTHECARY"
                    ]
                })
                callback("AGGRESSOR")
                break
            case "weaponType":
                setCurrentType({
                        type: "select",
                        default: "HEAVY_BOLTGUN",
                        values: [
                            "HEAVY_BOLTGUN",
                            "BOLT_PISTOL",
                            "PLASMA_GUN",
                            "COMBI_PLASMA_GUN",
                            "INFERNO_PISTOL"
                        ]
                    }
                )
                callback("HEAVY_BOLTGUN")
                break
            case "meleeWeapon":
                setCurrentType({
                        type: "select",
                        default: "POWER_SWORD",
                        values: [
                            "POWER_SWORD",
                            "CHAIN_AXE",
                            "MANREAPER",
                            "POWER_FIST"
                        ]
                    }
                )
                callback("POWER_SWORD")
                break
            default:
                setCurrentType({type: "input"})
        }
    }

    useEffect(() => {
        setFieldType()
    }, [field]);
    return {currentType}
}

export const variablesToExpressions = (filtersList) => {
    return filtersList.map(filter => filter.field + filter.operation + filter.extraValue)
}


