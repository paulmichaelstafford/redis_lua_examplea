local routeTagCacheName = tostring(ARGV[1])
local tagCacheName = tostring(ARGV[2])
local tagIdVariableName = tostring(ARGV[3])
local routeIdsString = ARGV[4]

local routeIds = {}
for routeId in routeIdsString:gmatch("%d*") do
    table.insert(routeIds, tonumber(routeId))
end

local setKeys = {}
for i, routeId in ipairs(routeIds) do
    table.insert(setKeys, routeTagCacheName .. ":" .. routeId .. ":idx")
end

--If we try to set
--local keys = unpack(setKeys)
--return redis.call("sinter", keys)
--It won't work
--https://stackoverflow.com/questions/38384713/redis-lua-script-unpack-returning-different-results

local tagIndexKeys = redis.call("sinter", unpack(setKeys))

local function mysplit (inputstr, sep)
    if sep == nil then
        sep = ":"
    end
    local t={}
    for str in string.gmatch(inputstr, "([^"..sep.."]+)") do
        table.insert(t, str)
    end
    return t
end

local tagKeys = {}
for i, tagIndexKey in ipairs(tagIndexKeys) do
    local tagKey = mysplit(tagIndexKey)[3];
    table.insert(tagKeys, tonumber(redis.call("HGET", tagCacheName .. ":" .. tagKey, tagIdVariableName)))
end

return tagKeys;