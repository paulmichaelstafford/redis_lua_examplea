local rootKey = tostring(ARGV[1])
local zSetName = tostring(ARGV[2])
local longitude = tostring(ARGV[3])
local latitude = tostring(ARGV[4])
local radius = tostring(ARGV[5])
local unit = tostring(ARGV[6])

local zSetKey = rootKey .. ":" .. zSetName

local points = redis.call("GEORADIUS", zSetKey, longitude, latitude, radius, unit, 'WITHHASH')

local function locate(table, value)
    for i = 1, #table do
        if table[i] == value then return true end
    end
    return false
end

redis.breakpoint()

local routeIds = {}
local positionMax = {}
local positionMin = {}
for i, point in ipairs(points) do
    local pointRouteCacheKey = rootKey .. ":" .. point[1]
    local routeId = tonumber(redis.call("HGET", pointRouteCacheKey, 'routeId'))
    local position = tonumber(redis.call("HGET", pointRouteCacheKey, 'position'))
    if locate(routeIds, routeId) == false then
        table.insert(routeIds, routeId)
    end

    if positionMax[routeId] == nil then
        positionMax[routeId] = position
    else
        if position > positionMax[routeId] then
            positionMax[routeId] = position
        end
    end

    if positionMin[routeId] == nil then
        positionMin[routeId] = position
    else
        if position < positionMin[routeId] then
            positionMin[routeId] = position
        end
    end

end

redis.breakpoint()

local routeIdsAndMinPositionAndMaxPosition = {}
for i, routeId in ipairs(routeIds) do
    table.insert(routeIdsAndMinPositionAndMaxPosition, { routeId,  positionMin[routeId], positionMax[routeId]})
end
return routeIdsAndMinPositionAndMaxPosition